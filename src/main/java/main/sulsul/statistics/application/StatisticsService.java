package main.sulsul.statistics.application;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.beverage.domain.Beverage;
import main.sulsul.beverage.dto.BeverageInfo;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.record.domain.DrunkenLevel;
import main.sulsul.statistics.dao.StatisticsRepository;
import main.sulsul.statistics.dto.RecordStats;
import main.sulsul.statistics.dto.Section1;
import main.sulsul.statistics.dto.Section2;
import main.sulsul.statistics.dto.Section3;
import main.sulsul.statistics.dto.TotalStatisticsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StatisticsService {

    private final MemberRepository memberRepository;
    private final StatisticsRepository statisticsRepository;

    public TotalStatisticsResponse getStatistics(Long memberId, LocalDate date) {

        final Member member = memberRepository.findById(memberId)
            .orElseThrow();

        // 총 3개월 기간 구하기
        final LocalDate startDate = date.minusMonths(2).withDayOfMonth(1);
        final LocalDate endDate = date.plusMonths(1).withDayOfMonth(1);
        final List<RecordStats> findRecords = statisticsRepository.findAllByMemberIdAndRecordedAtBetween(memberId,
                                                                                                         startDate,
                                                                                                         endDate);
        final LocalDate thisMonth = LocalDate.now().withDayOfMonth(1);

        // section1
        final Section1 section1 = getSection1(findRecords, thisMonth);

        // section2
        final List<Section2> section2 = getSection2(findRecords);

        // section3
        final Section3 section3 = getSection3(findRecords, thisMonth);

        return new TotalStatisticsResponse(member.getNickname(), section1, section2, section3);
    }

    /**
     * 이달의 음주량 구하기
     * @param findRecords
     * @param thisMonth
     * @return
     */
    private Section1 getSection1(List<RecordStats> findRecords, LocalDate thisMonth) {
        final Map<Beverage, Integer> section1Data = findRecords.stream()
            .filter(a -> thisMonth.equals(a.getRecordedAt().withDayOfMonth(1)))
            .collect(groupingBy(RecordStats::getBeverage, summingInt(RecordStats::getDrink)));

        if (section1Data.isEmpty()) {
            return null;
        }

        int totalBottle = 0;
        int totalDrink = 0;
        int onlyDrink = 0;

        final List<BeverageInfo> beverageInfos = new ArrayList<>();
        for (Entry<Beverage, Integer> beverageIntegerEntry : section1Data.entrySet()) {
            final Beverage beverage = beverageIntegerEntry.getKey();
            final Integer beverageDrink = beverageIntegerEntry.getValue();

            onlyDrink += beverageDrink;

            final BeverageInfo beverageInfo = beverage.calculateBottle(beverageDrink);
            totalBottle += beverageInfo.getBottle();
            totalDrink += beverageInfo.getDrink();
            beverageInfos.add(beverageInfo);
        }

        final Entry<Beverage, Integer> maxBeverage = section1Data.entrySet()
            .stream()
            .sorted(Entry.comparingByKey(Comparator.comparing(Beverage::toString)))
            .max(Entry.comparingByValue())
            .get();

        Entry<Beverage, Integer> minBeverage = section1Data.entrySet()
            .stream()
            .sorted(Entry.comparingByKey(Comparator.comparing(Beverage::toString)))
            .min(Entry.comparingByValue())
            .get();

        final BeverageInfo maxBeverageInfo = maxBeverage.getKey().calculateBottle(maxBeverage.getValue());
        final BeverageInfo minBeverageInfo = minBeverage.getKey().calculateBottle(minBeverage.getValue());

        final Section1 section1 = new Section1(
            totalBottle,
            totalDrink,
            onlyDrink,
            maxBeverage.getKey().getKorean(),
            maxBeverageInfo.getBottle(),
            maxBeverageInfo.getDrink(),
            minBeverage.getKey().getKorean(),
            minBeverageInfo.getBottle(),
            minBeverageInfo.getDrink(),
            beverageInfos
        );
        return section1;
    }

    /**
     * 최근 3개월 음주 빈도
     * @param findRecords
     * @return
     */
    private List<Section2> getSection2(List<RecordStats> findRecords) {

        final Set<LocalDate> recordDateSet = findRecords.stream()
            .map(RecordStats::getRecordedAt)
            .collect(toSet());

        final Map<LocalDate, Long> section2Data = recordDateSet.stream()
            .collect(groupingBy(r -> r.withDayOfMonth(1), counting()));

        if (section2Data.isEmpty()) {
            return new ArrayList<>();
        }

        List<Section2> section2Responses = new ArrayList<>();

        for (Entry<LocalDate, Long> section2 : section2Data.entrySet()) {
            section2Responses.add(new Section2(section2.getKey(), section2.getValue()));
        }
        section2Responses.sort(Comparator.comparing(Section2::getDate));
        return section2Responses;
    }

    /**
     * 이달의 컨디션
     * @param findRecords
     * @param thisMonth
     * @return
     */
    private Section3 getSection3(List<RecordStats> findRecords, LocalDate thisMonth) {
        final Map<LocalDate, DrunkenLevel> section3Data = findRecords.stream()
            .filter(a -> thisMonth.equals(a.getRecordedAt().withDayOfMonth(1)))
            .collect(toMap(RecordStats::getRecordedAt, RecordStats::getDrunkenLevel, (existingValue, newValue) -> existingValue));

        if (section3Data.isEmpty()) {
            return null;
        }

        Map<DrunkenLevel, Long> drunkenLevelCounts = section3Data.values().stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Integer drunkenLevel1Count = drunkenLevelCounts.getOrDefault(DrunkenLevel.DRUNKEN_LEVEL1, 0L).intValue();
        Integer drunkenLevel2Count = drunkenLevelCounts.getOrDefault(DrunkenLevel.DRUNKEN_LEVEL2, 0L).intValue();
        Integer drunkenLevel3Count = drunkenLevelCounts.getOrDefault(DrunkenLevel.DRUNKEN_LEVEL3, 0L).intValue();
        Integer drunkenLevel4Count = drunkenLevelCounts.getOrDefault(DrunkenLevel.DRUNKEN_LEVEL4, 0L).intValue();
        Integer drunkenLevel5Count = drunkenLevelCounts.getOrDefault(DrunkenLevel.DRUNKEN_LEVEL5, 0L).intValue();

        final Section3 section3 = new Section3(
            drunkenLevel1Count,
            drunkenLevel2Count,
            drunkenLevel3Count,
            drunkenLevel4Count,
            drunkenLevel5Count
        );
        return section3;
    }
}

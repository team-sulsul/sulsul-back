package main.sulsul.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.beverage.domain.Beverage;
import main.sulsul.beverage.dto.BeverageInfo;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.member.dto.MemberDto;
import main.sulsul.member.dto.MypageInfoResponse;
import main.sulsul.record.domain.dao.RecordBeverageRepository;
import main.sulsul.record.domain.dao.RecordRepository;
import main.sulsul.statistics.dao.StatisticsRepository;
import main.sulsul.statistics.dto.RecordStats;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;


@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final StatisticsRepository statisticsRepository;
    private final RecordBeverageRepository recordBeverageRepository;
    private final RecordRepository recordRepository;


    public MemberDto findByMemberId(Long memberId) {
        MemberDto memberDto = new MemberDto();
        Member member = memberRepository.findById(memberId)
                .orElseThrow();

        memberDto.setRefreshToken(member.getRefreshToken());
        memberDto.setId(memberId);
        memberDto.setRole(member.getRole());
        memberDto.setNickname(member.getNickname());
        memberDto.setUsername(member.getUsername());
        return memberDto;

    }

    public MypageInfoResponse myPageInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow();
        List<RecordStats> findRecords = statisticsRepository.findAllByMemberIdAndRecordedAtBetween(memberId, null, null);

        final Map<Beverage, Integer> groupingData = findRecords.stream()
                .collect(groupingBy(RecordStats::getBeverage, summingInt(RecordStats::getDrink)));

        if (groupingData.isEmpty()) {
            return new MypageInfoResponse(member.getNickname(), null, null);
        }

        int totalBottle = 0;
        int totalDrink = 0;

        for (Map.Entry<Beverage, Integer> beverageIntegerEntry : groupingData.entrySet()) {
            final Beverage beverage = beverageIntegerEntry.getKey();
            final Integer beverageDrink = beverageIntegerEntry.getValue();
            
            final BeverageInfo beverageInfo = beverage.calculateBottle(beverageDrink);
            totalBottle += beverageInfo.getBottle();
            totalDrink += beverageInfo.getDrink();
        }
        
        return new MypageInfoResponse(member.getNickname(), totalBottle, totalDrink);
    }

    @Transactional
    public void withdraw(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow();
        member.setUseYn("N");
    }
}

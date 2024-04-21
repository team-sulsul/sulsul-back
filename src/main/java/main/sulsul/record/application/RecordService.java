package main.sulsul.record.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import main.sulsul.record.domain.DrunkenLevel;
import main.sulsul.record.domain.Record;
import main.sulsul.record.domain.RecordBeverage;
import main.sulsul.record.domain.dao.RecordBeverageRepository;
import main.sulsul.record.domain.dao.RecordRepository;
import main.sulsul.record.dto.BeverageInfo;
import main.sulsul.record.dto.RecordBeverageRequest;
import main.sulsul.record.dto.RecordBulkRequest;
import main.sulsul.record.dto.RecordDrunkenLevelRequest;
import main.sulsul.record.dto.response.CalendarResponse;
import main.sulsul.record.exception.RecordErrorCode;
import main.sulsul.record.exception.RecordException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final RecordBeverageRepository recordBeverageRepository;

    public List<CalendarResponse> getRecords(Long memberId, String date) {
        final LocalDate parsedDate = LocalDate.parse(date + "-01");
        LocalDate startDate = parsedDate.withDayOfMonth(1);
        LocalDate endDate = parsedDate.withDayOfMonth(parsedDate.lengthOfMonth());

        final List<RecordBeverage> result = recordBeverageRepository.findAllByMemberIdAndRecordedAtBetween(
            memberId, startDate, endDate);

        return null;
    }


    /**
     * 유저의 음주 기록을 저장한다.
     *
     * @param memberId              유저 PK
     * @param recordBeverageRequest 음주 기록 DTO
     */
    @Transactional
    public Long recordBeverages(final Long memberId, final RecordBeverageRequest recordBeverageRequest) {
        final LocalDate recordedAt = recordBeverageRequest.getRecordedAt();
        if (recordRepository.findByMemberIdAndRecordedAt(memberId, recordedAt).isPresent()) {
            throw new RecordException(RecordErrorCode.ALREADY_EXIST);
        }

        final Record newRecord = recordRepository.save(
            new Record(memberId, DrunkenLevel.DRUNKEN_LEVEL_DEFAULT, recordedAt));

        final List<RecordBeverage> newRecordBeverages = recordBeverageRequest.getBeverages()
            .stream()
            .map(b -> new RecordBeverage(newRecord.getId(), b.getBeverage(), b.getQuantity()))
            .toList();
        recordBeverageRepository.saveAll(newRecordBeverages);

        return newRecord.getId();
    }

    @Transactional
    public void recordDrunkenLevel(final Long memberId, final RecordDrunkenLevelRequest recordDrunkenLevelRequest) {
        final Record foundRecord = recordRepository.findByMemberIdAndRecordedAt(memberId,
                                                                                recordDrunkenLevelRequest.getRecordedAt())
            .orElseThrow(() -> new RecordException(RecordErrorCode.RECORD_NOT_FOUND));

        foundRecord.changeDrunkenLevel(recordDrunkenLevelRequest.getDrunkenLevel());
    }

    @Transactional
    public void recordBulk(Long memberId, List<RecordBulkRequest> recordBulkRequests) {
        for (RecordBulkRequest recordRequest : recordBulkRequests) {
            final Record record = recordRepository.findByMemberIdAndRecordedAt(memberId, recordRequest.getRecordedAt())
                .orElseGet(() -> recordRepository.save(new Record(
                    memberId,
                    recordRequest.getDrunkenLevel(),
                    recordRequest.getRecordedAt())
                ));
            record.changeDrunkenLevel(recordRequest.getDrunkenLevel());

            List<RecordBeverage> recordBeverages = new ArrayList<>();
            for (BeverageInfo beverage : recordRequest.getBeverages()) {
                final Optional<RecordBeverage> findRecordBeverage = recordBeverageRepository.findByRecordIdAndBeverage(
                    record.getId(), beverage.getBeverage());
                if (findRecordBeverage.isPresent()) {
                    final RecordBeverage recordBeverage = findRecordBeverage.get();
                    recordBeverage.changeDrink(beverage.getQuantity());
                } else {
                    recordBeverages.add(
                        new RecordBeverage(record.getId(), beverage.getBeverage(), beverage.getQuantity()));
                }
            }
            recordBeverageRepository.saveAll(recordBeverages);
        }
    }
}

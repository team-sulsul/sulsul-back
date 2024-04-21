package main.sulsul.record.application;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.sulsul.record.domain.DrunkenLevel;
import main.sulsul.record.domain.Record;
import main.sulsul.record.domain.RecordBeverage;
import main.sulsul.record.domain.dao.RecordBeverageRepository;
import main.sulsul.record.domain.dao.RecordRepository;
import main.sulsul.record.dto.RecordBeverageRequest;
import main.sulsul.record.dto.RecordDrunkenLevelRequest;
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
}

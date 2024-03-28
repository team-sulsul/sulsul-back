package main.sulsul.record.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import main.sulsul.record.domain.Record;
import main.sulsul.record.domain.RecordBeverage;
import main.sulsul.record.domain.dao.RecordBeverageRepository;
import main.sulsul.record.domain.dao.RecordRepository;
import main.sulsul.record.dto.RecordRequest;
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
     * @param userId        유저 PK
     * @param recordRequest 음주 기록 DTO
     */
    @Transactional
    public void recordBeverages(final Long userId, final RecordRequest recordRequest) {
        final Record newRecord = recordRepository.save(
            new Record(userId, recordRequest.getHangoverLevel(), recordRequest.getRecordedAt()));

        final List<RecordBeverage> newRecordBeverages = recordRequest.getBeverages()
            .stream()
            .map(b -> new RecordBeverage(newRecord.getId(), b.getBeverage(), b.getDrink()))
            .toList();
        recordBeverageRepository.saveAll(newRecordBeverages);
    }
}

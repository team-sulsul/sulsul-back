package main.sulsul.beverage.application;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import main.sulsul.beverage.domain.HangoverLevel;
import main.sulsul.beverage.domain.Record;
import main.sulsul.beverage.domain.dao.RecordBeverageRepository;
import main.sulsul.beverage.domain.dao.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final RecordBeverageRepository recordBeverageRepository;

    @Transactional
    public void createRecord(Long memberId, HangoverLevel hangoverLevel, LocalDate date) {
        Record newRecord = new Record(memberId, hangoverLevel, date);
        recordRepository.save(newRecord);
    }
}

package main.sulsul.admin.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.beverage.domain.RecordBeverage;
import main.sulsul.beverage.domain.dao.RecordBeverageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminRecordService {

    private final RecordBeverageRepository recordBeverageRepository;
    public List<RecordBeverage> getRecordDetail(Long recordId) {
        return recordBeverageRepository.findAllByRecordId(recordId);
    }
}

package main.sulsul.record.domain.dao;

import main.sulsul.record.domain.RecordBeverage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordBeverageRepository extends JpaRepository<RecordBeverage, Long> {

    List<RecordBeverage> findAllByRecordId(Long recordId);

}

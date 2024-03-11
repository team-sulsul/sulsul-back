package main.sulsul.beverage.domain.dao;

import java.util.List;
import main.sulsul.beverage.domain.RecordBeverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordBeverageRepository extends JpaRepository<RecordBeverage, Long> {

    List<RecordBeverage> findAllByRecordId(Long recordId);

}

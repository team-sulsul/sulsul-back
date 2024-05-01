package main.sulsul.record.domain.dao;

import java.util.List;
import java.util.Optional;
import main.sulsul.beverage.domain.Beverage;
import main.sulsul.record.domain.RecordBeverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordBeverageRepository extends JpaRepository<RecordBeverage, Long> {

    List<RecordBeverage> findAllByRecordId(Long recordId);

    List<RecordBeverage> findAllByRecordIdAndBeverageIn(Long recordId, List<Beverage> beverages);

    Optional<RecordBeverage> findByRecordIdAndBeverage(Long recordId, Beverage beverage);

    void deleteAllByRecordIdAndBeverageIn(Long recordId, List<Beverage> beverage);
}

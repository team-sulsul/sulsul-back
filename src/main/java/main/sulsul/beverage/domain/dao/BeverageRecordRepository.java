package main.sulsul.beverage.domain.dao;

import main.sulsul.beverage.domain.RecordBeverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRecordRepository extends JpaRepository<RecordBeverage, Long> {
}

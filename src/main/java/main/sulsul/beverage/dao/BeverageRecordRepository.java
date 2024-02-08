package main.sulsul.beverage.dao;

import main.sulsul.beverage.RecordBeverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRecordRepository extends JpaRepository<RecordBeverage, Long> {
}

package main.sulsul.beverage.dao;

import main.sulsul.beverage.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}

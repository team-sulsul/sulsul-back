package main.sulsul.beverage.domain.dao;

import main.sulsul.beverage.domain.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {

    Page<Record> findAllByMemberId(final Long memberId, Pageable pageable);
}

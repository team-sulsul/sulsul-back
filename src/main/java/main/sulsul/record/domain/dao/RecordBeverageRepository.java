package main.sulsul.record.domain.dao;

import java.time.LocalDate;
import java.util.Optional;
import main.sulsul.beverage.domain.Beverage;
import main.sulsul.record.domain.RecordBeverage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordBeverageRepository extends JpaRepository<RecordBeverage, Long> {

    List<RecordBeverage> findAllByRecordId(Long recordId);

    Optional<RecordBeverage> findByRecordIdAndBeverage(Long recordId, Beverage beverage);

    @Query("""
        select rb from RecordBeverage rb
        join fetch Record r on rb.recordId = r.id
        where r.memberId = :memberId and r.recordedAt between :startDate and :endDate
        """)
    List<RecordBeverage> findAllByMemberIdAndRecordedAtBetween(
        @Param("memberId") Long memberId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}

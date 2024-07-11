package main.sulsul.statistics.dao;

import static main.sulsul.record.domain.QRecord.record;
import static main.sulsul.record.domain.QRecordBeverage.recordBeverage;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.sulsul.statistics.dto.QRecordStats;
import main.sulsul.statistics.dto.RecordStats;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StatisticsRepository {

    private final JPAQueryFactory queryFactory;

    public List<RecordStats> findAllByMemberIdAndRecordedAtBetween(Long memberId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
            .select(new QRecordStats(record.id, record.memberId, record.drunkenLevel, record.recordedAt, recordBeverage.beverage, recordBeverage.drink))
            .from(record)
            .leftJoin(recordBeverage).on(record.id.eq(recordBeverage.recordId))
            .where(
                    record.memberId.eq(memberId)
                       .and(dateBetween(startDate, endDate))
            )
            .fetch();
    }
    
    private static BooleanExpression dateBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return null;
        }
        return record.recordedAt.between(startDate, endDate);
    }
}

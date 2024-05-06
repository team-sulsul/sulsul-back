package main.sulsul.record.domain.dao.impl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static main.sulsul.record.domain.QRecord.record;
import static main.sulsul.record.domain.QRecordBeverage.recordBeverage;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.sulsul.record.domain.dao.RecordCustomRepository;
import main.sulsul.record.dto.QBeverageInfo;
import main.sulsul.record.dto.response.CalendarResponse;
import main.sulsul.record.dto.response.QCalendarResponse;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RecordCustomRepositoryImpl implements RecordCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CalendarResponse> getRecordBulk(Long memberId) {
        return jpaQueryFactory
            .select(record, recordBeverage)
            .from(record)
            .leftJoin(recordBeverage).on(record.id.eq(recordBeverage.recordId))
            .where(record.memberId.eq(memberId))
            .transform(
                groupBy(record.id)
                    .list(
                        new QCalendarResponse(
                            record.id,
                            record.memberId,
                            record.recordedAt,
                            record.drunkenLevel,
                            list(new QBeverageInfo(recordBeverage.drink, recordBeverage.beverage))
                        )
                    )
            );
    }
}

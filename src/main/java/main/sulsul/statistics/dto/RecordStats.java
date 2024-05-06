package main.sulsul.statistics.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.Getter;
import main.sulsul.beverage.domain.Beverage;
import main.sulsul.record.domain.DrunkenLevel;

@Getter
public class RecordStats {

    private Long recordId;

    private Long memberId;

    private DrunkenLevel drunkenLevel;

    private LocalDate recordedAt;

    private Beverage beverage;

    private Integer drink;

    @QueryProjection
    public RecordStats(Long recordId, Long memberId, DrunkenLevel drunkenLevel, LocalDate recordedAt, Beverage beverage,
                       Integer drink) {
        this.recordId = recordId;
        this.memberId = memberId;
        this.drunkenLevel = drunkenLevel;
        this.recordedAt = recordedAt;
        this.beverage = beverage;
        this.drink = drink;
    }
}

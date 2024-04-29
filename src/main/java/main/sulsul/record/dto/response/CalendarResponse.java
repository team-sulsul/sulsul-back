package main.sulsul.record.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.sulsul.record.domain.DrunkenLevel;
import main.sulsul.record.dto.BeverageInfo;

@Getter
@NoArgsConstructor
public class CalendarResponse {

    private Long recordId;

    private Long memberId;

    private LocalDate recordedAt;

    private DrunkenLevel drunkenLevel;

    private List<BeverageInfo> beverages;

    @QueryProjection

    public CalendarResponse(Long recordId, Long memberId, LocalDate recordedAt, DrunkenLevel drunkenLevel,
                            List<BeverageInfo> beverages) {
        this.recordId = recordId;
        this.memberId = memberId;
        this.recordedAt = recordedAt;
        this.drunkenLevel = drunkenLevel;
        this.beverages = beverages;
    }
}

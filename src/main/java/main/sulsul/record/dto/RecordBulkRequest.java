package main.sulsul.record.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import main.sulsul.record.domain.DrunkenLevel;

@Getter
public class RecordBulkRequest {

    private LocalDate recordedAt;

    private DrunkenLevel drunkenLevel;

    List<BeverageInfo> beverages;
}

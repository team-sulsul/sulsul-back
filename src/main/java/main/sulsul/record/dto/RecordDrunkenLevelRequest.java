package main.sulsul.record.dto;

import java.time.LocalDate;
import lombok.Getter;
import main.sulsul.record.domain.DrunkenLevel;

@Getter
public class RecordDrunkenLevelRequest {

    private LocalDate recordedAt;

    private DrunkenLevel drunkenLevel;
}

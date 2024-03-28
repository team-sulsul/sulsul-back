package main.sulsul.record.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import main.sulsul.record.domain.HangoverLevel;

@Getter
public class RecordRequest {

    private LocalDate recordedAt;

    private HangoverLevel hangoverLevel;

    private List<BeverageRequest> beverages;
}

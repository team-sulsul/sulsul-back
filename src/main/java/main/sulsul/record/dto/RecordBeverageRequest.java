package main.sulsul.record.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class RecordBeverageRequest {

    private LocalDate recordedAt;

    private List<BeverageRequest> beverages;
}

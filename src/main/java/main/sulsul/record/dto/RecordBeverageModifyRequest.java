package main.sulsul.record.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.sulsul.beverage.domain.Beverage;

@NoArgsConstructor
@Getter
public class RecordBeverageModifyRequest {

    private LocalDate recordedAt;

    private List<BeverageInfo> beverages;

    private List<Beverage> deleteBeverages;
}

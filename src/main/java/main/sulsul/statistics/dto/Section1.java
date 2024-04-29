package main.sulsul.statistics.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.sulsul.beverage.dto.BeverageInfo;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Section1 {

    private Integer totalBottle;

    private Integer totalDrink;

    private Integer onlyDrink;

    private String maxBeverage;

    private Integer maxBeverageBottle;

    private Integer maxBeverageDrink;

    private String minBeverage;

    private Integer minBeverageBottle;

    private Integer minBeverageDrink;

    private List<BeverageInfo> beverageInfos;
}

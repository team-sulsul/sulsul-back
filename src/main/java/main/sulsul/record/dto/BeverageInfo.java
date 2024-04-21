package main.sulsul.record.dto;

import lombok.Getter;
import main.sulsul.beverage.domain.Beverage;

@Getter
public class BeverageInfo {

    private int quantity;

    private Beverage beverage;
}

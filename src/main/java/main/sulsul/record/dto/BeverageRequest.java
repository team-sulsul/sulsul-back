package main.sulsul.record.dto;

import lombok.Getter;
import main.sulsul.beverage.domain.Beverage;

@Getter
public class BeverageRequest {

    private int drink;

    private Beverage beverage;
}

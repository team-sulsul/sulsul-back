package main.sulsul.beverage.dto;

import lombok.Getter;

@Getter
public class BeverageInfo {

    private String name;

    private Integer bottle;

    private Integer drink;

    private Integer onlyDrink;

    public BeverageInfo(String name, Integer bottle, Integer drink, Integer onlyDrink) {
        this.name = name;
        this.bottle = bottle;
        this.drink = drink;
        this.onlyDrink = onlyDrink;
    }
}

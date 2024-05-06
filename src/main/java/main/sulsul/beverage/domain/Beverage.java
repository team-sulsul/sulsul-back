package main.sulsul.beverage.domain;

import lombok.Getter;
import main.sulsul.beverage.dto.BeverageInfo;

@Getter
public enum Beverage {

    SOJU("소주", 360, 7),
    BEER("맥주", 500, 3),
    SOJU_BEER("소맥", null, null),
    WINE("와인", 750, 5),
    RICE_WINE("막걸리", 750, 5),
    COCKTAIL("칵테일", null, null),
    WHISKY("위스키", null, null),
    VODKA("보드카", 750, 25),
    SAKE("사케", 300, 7);

    private final String korean;

    private final Integer capacity;

    private final Integer drinkPerBottle;

    Beverage(String korean, Integer capacity, Integer drinkPerBottle) {
        this.korean = korean;
        this.capacity = capacity;
        this.drinkPerBottle = drinkPerBottle;
    }

    public BeverageInfo calculateBottle(Integer drink) {
        if (drinkPerBottle == null) {
            return new BeverageInfo(korean, null, drink, drink);
        }
        return new BeverageInfo(korean, drink / drinkPerBottle, drink % drinkPerBottle, drink);
    }
}

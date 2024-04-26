package main.sulsul.record.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import main.sulsul.beverage.domain.Beverage;

@Getter
public class BeverageInfo {

    private int quantity;

    private Beverage beverage;

    @QueryProjection
    public BeverageInfo(int quantity, Beverage beverage) {
        this.quantity = quantity;
        this.beverage = beverage;
    }
}

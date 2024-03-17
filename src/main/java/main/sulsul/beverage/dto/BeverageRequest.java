package main.sulsul.beverage.dto;

import lombok.Getter;

@Getter
public class BeverageRequest {

    private Long beverageId;

    private int bottles;

    private int glasses;
}

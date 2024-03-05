package main.sulsul.beverage.domain;

import lombok.Getter;

@Getter
public enum HangoverLevel {

    SOBER(1, "멀쩡해요"),
    TIPSY(2, "알딸딸"),
    DRUNK(3, "힘들어요"),
    INTOXICATED(4, "취했어요"),
    WASTED(5, "핑글핑글");

    private final int level;
    private final String name;

    HangoverLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }
}

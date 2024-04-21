package main.sulsul.record.domain;

import lombok.Getter;

@Getter
public enum DrunkenLevel {

    DRUNKEN_LEVEL_DEFAULT(0, "미정"),
    DRUNKEN_LEVEL1(1, "멀쩡해요"),
    DRUNKEN_LEVEL2(2, "알딸딸"),
    DRUNKEN_LEVEL3(3, "힘들어요"),
    DRUNKEN_LEVEL4(4, "취했어요"),
    DRUNKEN_LEVEL5(5, "핑글핑글");

    private final int level;
    private final String name;

    DrunkenLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }
}

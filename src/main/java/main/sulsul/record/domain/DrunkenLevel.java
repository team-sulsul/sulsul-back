package main.sulsul.record.domain;

import lombok.Getter;

@Getter
public enum DrunkenLevel {

    DRUNKEN_LEVEL_DEFAULT(0, "미정", "멀쩡한"),
    DRUNKEN_LEVEL_1(1, "멀쩡해요", "멀쩡한"),
    DRUNKEN_LEVEL_2(2, "알딸딸", "알딸딸한"),
    DRUNKEN_LEVEL_3(3, "힘들어요", "힘든"),
    DRUNKEN_LEVEL_4(4, "취했어요", "취한"),
    DRUNKEN_LEVEL_5(5, "핑글핑글", "핑글핑글한");

    private final int level;
    private final String name;
    private final String status;

    DrunkenLevel(int level, String name, String status) {
        this.level = level;
        this.name = name;
        this.status = status;
    }
}

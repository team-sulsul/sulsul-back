package main.sulsul.member.domain;


import lombok.Getter;

@Getter
public enum Role {
    USER("카카오유저"),
    GUEST("게스트");

    private final String message;

    Role(String message) {
        this.message = message;
    }
}

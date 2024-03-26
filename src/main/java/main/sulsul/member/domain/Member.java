package main.sulsul.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.sulsul.global.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "idx__username", columnList = "username", unique = true)
})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private Role role;

    @Column(name = "refresh_token", length = 100)
    private String refreshToken;

    private String password;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Builder
    public Member(Long id, String email, String username, Role role, String refreshToken, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
        this.refreshToken = refreshToken;
        this.password = password;
    }
}

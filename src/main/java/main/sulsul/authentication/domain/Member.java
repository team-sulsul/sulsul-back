package main.sulsul.authentication.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.sulsul.authentication.domain.oauth.OAuthProvider;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "o_auth_provider", length = 20)
    private OAuthProvider oAuthProvider;
    @Column(name = "refresh_token", length = 100)
    private String refreshToken;



    @Builder
    public Member(Long id, String email, String nickname, OAuthProvider oAuthProvider, String refreshToken) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.oAuthProvider = oAuthProvider;
        this.refreshToken = refreshToken;
    }
}

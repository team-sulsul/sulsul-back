package main.sulsul.oauth.domain.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokensDTO {
    private String accessToken;
    private String refreshToken;
    private String message;

}


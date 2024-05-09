package main.sulsul.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokensResponse {
    private String accessToken;

    public static AuthTokensResponse of(String accessToken) {
        return new AuthTokensResponse(accessToken);
    }
}


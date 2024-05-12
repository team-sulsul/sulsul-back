package main.sulsul.oauth.domain.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import main.sulsul.oauth.domain.oauth.OAuthLoginParams;
import main.sulsul.oauth.domain.oauth.OAuthProvider;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@ToString
@NoArgsConstructor
public class LoginParams implements OAuthLoginParams {

    private String accessToken;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("accessToken", accessToken);
        return body;
    }
}

package main.sulsul.oauth.domain.token;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokensGeneratorTest {

    private static final String secretKey = "testSecretKey20230327testSecretKey20230327testSecretKey202303271221122121212122121212112212121221212112211212121212121221";
    private static final long accessTokenExpiration = 360000;
    private static final long refreshTokenExpiration = 604800000;

    private JwtTokensGenerator jwtTokensGenerator = new JwtTokensGenerator(
        new JwtTokenProvider(secretKey, accessTokenExpiration, refreshTokenExpiration), null, null);

    @Test
    @DisplayName("가입된 멤버면 accessToken 반환")
    void generateAccessToken() {
        final String accessToken = jwtTokensGenerator.generateAccessToken(4L);
        assertThat(accessToken).isNotBlank();
    }
}

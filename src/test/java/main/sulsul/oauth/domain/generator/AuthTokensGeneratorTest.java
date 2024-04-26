package main.sulsul.oauth.domain.generator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthTokensGeneratorTest {

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Test
    @DisplayName("가입된 멤버면 accessToken 반환")
    void generateAccessToken() {
        final String accessToken = authTokensGenerator.generateAccessToken(4L);
        System.out.println(accessToken);
    }
}

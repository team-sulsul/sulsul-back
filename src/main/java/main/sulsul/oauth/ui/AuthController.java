package main.sulsul.oauth.ui;

import lombok.RequiredArgsConstructor;
import main.sulsul.oauth.application.OAuthLoginService;
import main.sulsul.oauth.domain.generator.AuthTokens;
import main.sulsul.oauth.domain.kakao.KakaoLoginParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
}

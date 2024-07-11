package main.sulsul.oauth.ui;

import lombok.RequiredArgsConstructor;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.oauth.application.OAuthLoginService;
import main.sulsul.oauth.domain.kakao.KakaoLoginParams;
import main.sulsul.oauth.domain.kakao.LoginParams;
import main.sulsul.oauth.dto.AuthTokensResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/accesstoken/generate")
    public ResponseEntity<String> accessTokenGenerate(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.accessTokenGen(params));
    }

    /**
     * 카카오 회원가입 및 토큰을 발급해준다.
     * @param params 카카오 액세스 토큰
     * @return
     */
    @PostMapping("/kakao")
    public CommonResponse<AuthTokensResponse> registerKakao(@RequestBody KakaoLoginParams params) {
        return CommonResponse.ok(oAuthLoginService.registerKakao(params));
    }

    /**
     * 로그인 시도를 한다.
     * @param params 토큰 정보
     * @return
     */
    @PostMapping("/login")
    public CommonResponse<Void> login(@RequestBody LoginParams params) {
        oAuthLoginService.login(params);
        return CommonResponse.ok(null);
    }
}

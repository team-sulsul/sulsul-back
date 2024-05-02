package main.sulsul.oauth.ui;

import lombok.RequiredArgsConstructor;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.oauth.application.OAuthLoginService;
import main.sulsul.oauth.domain.generator.AuthTokens;
import main.sulsul.oauth.domain.generator.AuthTokensDTO;
import main.sulsul.oauth.domain.kakao.KakaoLoginParams;
import main.sulsul.oauth.domain.kakao.LoginParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/accesstoken/generate")
    public ResponseEntity<String> accessTokenGenerate(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.accessTokenGen(params));
    }

    @PostMapping("/kakao")
    public CommonResponse<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
        return CommonResponse.ok(oAuthLoginService.login(params));
    }



    @PostMapping("/login")
    public CommonResponse<AuthTokensDTO> isLogin(@RequestBody LoginParams params) {
        return CommonResponse.ok(oAuthLoginService.isLogin(params));
    }


    @PostMapping("/withdraw/{id}")
    public CommonResponse<String> withdraw(@PathVariable Long id) {
        return CommonResponse.ok(oAuthLoginService.withdraw(id));
    }
}

package main.sulsul.temp;

import lombok.RequiredArgsConstructor;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.oauth.domain.token.JwtTokensGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/temp")
public class TempController {

    private final JwtTokensGenerator jwtTokensGenerator;

    @GetMapping("/accessToken/{memberId}")
    public CommonResponse<String> getAccessTokenByMemberId(@PathVariable("memberId") Long memberId) {
        final String accessToken = jwtTokensGenerator.generateAccessToken(memberId);
        return CommonResponse.ok(accessToken);
    }
}

package main.sulsul.oauth.application;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.Role;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.oauth.domain.kakao.LoginParams;
import main.sulsul.oauth.domain.oauth.OAuthInfoResponse;
import main.sulsul.oauth.domain.oauth.OAuthLoginParams;
import main.sulsul.oauth.domain.oauth.RequestOAuthInfoService;
import main.sulsul.oauth.domain.token.JwtTokenProvider;
import main.sulsul.oauth.domain.token.JwtTokensGenerator;
import main.sulsul.oauth.domain.token.TokenValidator;
import main.sulsul.oauth.dto.AuthTokensResponse;
import main.sulsul.oauth.exception.OAuthErrorCode;
import main.sulsul.oauth.exception.OAuthException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthLoginService {

    private static final String SIMPLE_PASSWORD = "1111";
    private final MemberRepository memberRepository;
    private final JwtTokensGenerator jwtTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final NicknameGenerator nicknameGenerator;
    private final TokenValidator tokenValidator;

    public String accessTokenGen(OAuthLoginParams params) {
        return getAccessToken(params);
    }

    @Transactional
    public AuthTokensResponse registerKakao(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return jwtTokensGenerator.generate(memberId);
    }

    @Transactional
    public String withdraw(Long id) {
        Optional<Member> newMember = memberRepository.findById(id);
        newMember.ifPresent(member -> {
            member.setUseYn("N");
        });

        return "delete";
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByUsername(oAuthInfoResponse.getEmail())
            .map(Member::getId)
            .orElseGet(() -> createNewMember(oAuthInfoResponse));
    }

    private Long createNewMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
            .nickname(nicknameGenerator.getNickname())
            .username(oAuthInfoResponse.getEmail())
            .role(Role.USER)
            .password(passwordEncoder.encode(SIMPLE_PASSWORD))
            .useYn("Y")
            .build();
        return memberRepository.save(member).getId();
    }

    /**
     * accessToken, refreshToken 토큰 만료 여부 체크 - accessToken이 이상 없으면 200 응답 - accessToken이 만료되면 refreshToken으로 유효성 검증 -
     * refreshToken이 유효하면 새로운 accessToken 과 함께 400 코드 반환 - refreshToken 도 유효하지 않으면 401 코드 반환. 이때는 클라에서 kakao로 재로그인 해야함
     *
     * @param params
     * @return
     */
    public void login(LoginParams params) {
        String accessToken = params.getAccessToken();

        try {
            jwtTokenProvider.validate(accessToken);
        } catch (ExpiredJwtException e) {
            log.info("액세스 토큰 만료. 리프레쉬 토큰 검증 실행");
            tokenValidator.handleExpiredAccessToken(e);
        } catch (SignatureException | MalformedJwtException e) {
            throw new OAuthException(OAuthErrorCode.TOKEN_INVALID);
        }
    }

    private String getAccessToken(OAuthLoginParams params) {
        return requestOAuthInfoService.getAccessToken(params);
    }
}

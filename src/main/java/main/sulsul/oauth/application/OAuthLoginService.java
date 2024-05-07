package main.sulsul.oauth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import java.util.Date;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.Role;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.oauth.domain.generator.AuthTokens;
import main.sulsul.oauth.domain.generator.AuthTokensDTO;
import main.sulsul.oauth.domain.generator.AuthTokensGenerator;
import main.sulsul.oauth.domain.generator.JwtTokenProvider;
import main.sulsul.oauth.domain.kakao.LoginParams;
import main.sulsul.oauth.domain.oauth.OAuthInfoResponse;
import main.sulsul.oauth.domain.oauth.OAuthLoginParams;
import main.sulsul.oauth.domain.oauth.RequestOAuthInfoService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginService {

    public static final String SIMPLE_PASSWORD = "1111";
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final JwtTokenProvider jwtTokenProvider;
    public final PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static byte[] decodeKey = Decoders.BASE64.decode("testSecretKey20230327testSecretKey20230327testSecretKey202303271221122121212122121212112212121221212112211212121212121221");

    public String accessTokenGen(OAuthLoginParams params) {
        return getAccessToken(params);
    }

    public static String randomName() {
        String apiUrl = "https://nickname.hwanmoo.kr/?format=text&max_length=5";

        // WebClient 객체 생성
        WebClient webClient = WebClient.create();

        // API 호출 및 응답 처리 (동기적)
        String response = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
    public AuthTokens login(OAuthLoginParams params) {
        log.info("loginTry");
        return getAuthTokens(params);
    }

    public String withdraw(Long id) {
        Optional<Member> newMember = memberRepository.findById(id);
        newMember.ifPresent(member -> {
            member.setUse_yn("N");
            memberRepository.save(member); // 업데이트된 Member를 저장
        });

        return "delete";
    }



    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByUsername(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Optional<Long> findMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByUsername(oAuthInfoResponse.getEmail())
                .map(Member::getId);
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .nickname(randomName())
                .username(oAuthInfoResponse.getEmail())
                .role(Role.USER)
                .password(passwordEncoder().encode(SIMPLE_PASSWORD))
                .use_yn("Y")
                .build();
        return memberRepository.save(member).getId();
    }

    public AuthTokensDTO isLogin(LoginParams params) {
        AuthTokensDTO authTokensDTO = new AuthTokensDTO();
        String accessToken = params.getAccessToken();
        String refreshToken = params.getRefreshToken();
        return isAccessTokenValid(accessToken, refreshToken, authTokensDTO, params);
    }

    public AuthTokensDTO isAccessTokenValid(String accessToken, String refreshToken, AuthTokensDTO authTokensDTO, LoginParams params) {
        try {
            isTokenExpired(accessToken);
            authTokensDTO.setMessage("200");
            authTokensDTO.setAccessToken("-");
            authTokensDTO.setRefreshToken("-");
            return authTokensDTO;
        } catch (ExpiredJwtException e) {
            try {
                isTokenExpired(refreshToken);
                authTokensDTO.setMessage("601");
                return authTokensDTO;
            }catch (ExpiredJwtException e2) {
                authTokensDTO.setMessage("600");
                log.info("param : {}", params);
                String id = jwtTokenProvider.extractSubject(params.getRefreshToken());
                log.info("추출된 refreshToken id: {}", id);
                String updateAccessToken = getUpdateAuthTokens(id);
                authTokensDTO.setAccessToken(updateAccessToken);
                return authTokensDTO;
            }
        }
    }

    public static boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    // 토큰에서 만료 시간 가져오기
    private static Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(decodeKey).build().parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }

    private String getAccessToken(OAuthLoginParams params) {
        return requestOAuthInfoService.getAccessToken(params);
    }

    private AuthTokens getAuthTokens(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private String getUpdateAuthTokens(String id) {
        return authTokensGenerator.generateAccessToken(Long.valueOf(id));
    }
}

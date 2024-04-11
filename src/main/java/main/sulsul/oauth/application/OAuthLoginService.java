package main.sulsul.oauth.application;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.Role;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.oauth.domain.generator.AuthTokens;
import main.sulsul.oauth.domain.generator.AuthTokensDTO;
import main.sulsul.oauth.domain.generator.AuthTokensGenerator;
import main.sulsul.oauth.domain.kakao.LoginParams;
import main.sulsul.oauth.domain.oauth.OAuthInfoResponse;
import main.sulsul.oauth.domain.oauth.OAuthLoginParams;
import main.sulsul.oauth.domain.oauth.RequestOAuthInfoService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static main.sulsul.oauth.domain.generator.JwtTokenProvider.isTokenExpired;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
    public final PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static byte[] decodeKey = Decoders.BASE64.decode("testSecretKey20230327testSecretKey20230327testSecretKey202303271221122121212122121212112212121221212112211212121212121221");

    public AuthTokens login(OAuthLoginParams params) {
        return getAuthTokens(params);
    }



    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .username(oAuthInfoResponse.getNickname())
                .role(Role.USER)
                .password(passwordEncoder().encode("1111"))
                .build();
        return memberRepository.save(member).getId();
    }

    public AuthTokensDTO isLogin(LoginParams params) {
        log.info("test");
        AuthTokensDTO authTokensDTO = new AuthTokensDTO();
        String accessToken = params.getAccessToken();
        String refreshToken = params.getRefreshToken();
        log.info("params : {}",params.getAccessToken());
        log.info("params : {}",params.getRefreshToken());
        return isAccessTokenValid(accessToken, authTokensDTO, params);
    }

    public AuthTokensDTO isAccessTokenValid(String accessToken,AuthTokensDTO authTokensDTO, LoginParams params) {

//        if (isTokenExpired(accessToken)) {
//            authTokensDTO.setMessage("200");
//            authTokensDTO.setAccessToken("-");
//            authTokensDTO.setRefreshToken("-");
//            return authTokensDTO;
//        } else {
//            // AccessToken이 만료된 경우
//            System.out.println("AccessToken이 만료되었습니다.");
//            authTokensDTO.setMessage("600");
//            AuthTokensDTO authTokens = getUpdateAuthTokens(params);
//            log.info("authToken : ", authTokens.getAccessToken());
//            log.info("authToken : ", authTokens.getRefreshToken());
//            return authTokensDTO;
//        }

        try {
            isTokenExpired(accessToken);
            authTokensDTO.setMessage("200");
            authTokensDTO.setAccessToken("-");
            authTokensDTO.setRefreshToken("-");
            return authTokensDTO;
        } catch (ExpiredJwtException e) {
            System.out.println("AccessToken이 만료되었습니다.");
            authTokensDTO.setMessage("600");
            AuthTokensDTO authTokens = getUpdateAuthTokens(params);
            log.info("authToken : ", authTokens.getAccessToken());
            log.info("authToken : ", authTokens.getRefreshToken());
            return authTokensDTO;
        }

//        try {
//            System.out.println("토큰이 정상적입니다.");
//            isTokenExpired(accessToken);
//            authTokensDTO.setMessage("200");
//            authTokensDTO.setAccessToken("-");
//            authTokensDTO.setRefreshToken("-");
//            return authTokensDTO;
//        } catch (ExpiredJwtException e) {
//            // AccessToken이 만료된 경우
//            System.out.println("AccessToken이 만료되었습니다.");
//            authTokensDTO.setMessage("600");
//            AuthTokens authTokens = getUpdateAuthTokens(params);
//            log.info("authToken : ", authTokens.getAccessToken());
//            log.info("authToken : ", authTokens.getRefreshToken());
//            return authTokensDTO;
//        } catch (MalformedJwtException | SignatureException e) {
//            // 유효하지 않은 형식의 토큰 또는 서명 오류가 발생한 경우
//            System.out.println("유효하지 않은 AccessToken입니다.");
//            authTokensDTO.setMessage("601");
//            return authTokensDTO;
//        } catch (Exception e) {
//            // 그 외 예외 처리
//            e.printStackTrace();
//            return authTokensDTO;
//        }
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

    private AuthTokens getAuthTokens(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private AuthTokensDTO getUpdateAuthTokens(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.updateToken(memberId);
    }
}

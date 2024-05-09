package main.sulsul.oauth.domain.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.oauth.exception.OAuthErrorCode;
import main.sulsul.oauth.exception.OAuthException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenValidator {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Long extractMemberId(String accessToken) {
        try {
            return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
        } catch (ExpiredJwtException e) {
            handleExpiredAccessToken(e);
        } catch (SignatureException | MalformedJwtException e) {
            throw new OAuthException(OAuthErrorCode.TOKEN_INVALID);
        }
        return null;
    }

    public void handleExpiredAccessToken(ExpiredJwtException e) {
        final Claims claims = e.getClaims();
        final String subject = claims.getSubject();
        final Long memberId = Long.valueOf(subject);
        final Member member = memberRepository.findById(memberId)
            .orElseThrow();
        final String refreshToken = member.getRefreshToken();
        try {
            jwtTokenProvider.validate(refreshToken);
            final String newAccessToken = jwtTokenProvider.generateAccessToken(subject);
            final Map<String, Object> map = new HashMap<>();
            map.put("accessToken", newAccessToken);
            throw new OAuthException(OAuthErrorCode.ACCESS_TOKEN_RENEWAL, map);
        } catch (ExpiredJwtException e2) {
            throw new OAuthException(OAuthErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }
}

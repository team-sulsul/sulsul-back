package main.sulsul.oauth.domain.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.oauth.exception.OAuthErrorCode;
import main.sulsul.oauth.exception.OAuthException;
import org.codehaus.groovy.syntax.TokenException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenValidator {

    private static final String PREFIX = "Bearer ";
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Long extractMemberId(HttpServletRequest request) {
        try {
            final String authorization = request.getHeader("Authorization");

            if (authorization == null) {
                throw new OAuthException(OAuthErrorCode.TOKEN_NOT_EXIST);
            }

            final String prefix = authorization.substring(0, 7);
            if (!prefix.startsWith(PREFIX)) {
                throw new MalformedJwtException("유효하지 않은 prefix");
            }

            final String token = authorization.substring(7);

            return Long.valueOf(jwtTokenProvider.extractSubject(token));
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

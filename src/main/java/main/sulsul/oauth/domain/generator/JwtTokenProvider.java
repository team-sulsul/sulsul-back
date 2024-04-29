package main.sulsul.oauth.domain.generator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.oauth.exception.OAuthErrorCode;
import main.sulsul.oauth.exception.OAuthException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generate(String subject, Date expiredAt) {
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(expiredAt)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return claims.getSubject();
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        } catch (ExpiredJwtException e) {
            // AccessToken 만료된 경우
            log.info("만료된 AccessToken {}", accessToken);
            throw new OAuthException(OAuthErrorCode.TOKEN_EXPIRED);
        } catch (MalformedJwtException | SignatureException e) {
            // 유효하지 않은 형식의 토큰 또는 서명 오류가 발생한 경우
            log.info("유효하지 않은 AccessToken {}", accessToken);
            throw new OAuthException(OAuthErrorCode.TOKEN_INVALID);
        } catch (Exception e) {
            // 그 외 예외 처리
            log.error("예외 발생", e);
            throw new OAuthException(OAuthErrorCode.TOKEN_INVALID);
        }
    }
}


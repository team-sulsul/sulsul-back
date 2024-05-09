package main.sulsul.oauth.domain.token;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenExpiration;
    private final long refreshExpiration;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey,
                            @Value("${jwt.access.expiration}") long accessTokenExpiration,
                            @Value("${jwt.refresh.expiration}") long refreshExpiration) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateAccessToken(String subject) {
        final long now = new Date().getTime();
        final Date date = new Date(now + accessTokenExpiration);
        return generate(subject, date);
    }

    public String generateRefreshToken(String subject) {
        final long now = new Date().getTime();
        final Date date = new Date(now + refreshExpiration);
        return generate(subject, date);
    }

    private String generate(String subject, Date expiredAt) {
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(expiredAt)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public String extractSubject(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    /**
     * 토큰 유효성 검사 ExpiredJwtException는 밖에서 처리할 거라 그대로 던짐
     *
     * @param token
     */
    public void validate(String token) {
        try {
            parseClaims(token);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (MalformedJwtException | SignatureException e) {
            // 유효하지 않은 형식의 토큰 또는 서명 오류가 발생한 경우
            throw e;
        } catch (Exception e) {
            // 그 외 예외 처리
            log.error("예외 발생", e);
            throw e;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}


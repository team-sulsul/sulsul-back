package main.sulsul.oauth.domain.generator;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;

    private static byte[] decodeKey = Decoders.BASE64.decode("testSecretKey20230327testSecretKey20230327testSecretKey202303271221122121212122121212112212121221212112211212121212121221");

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
        isAccessTokenValid(accessToken);
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
            return e.getClaims();
        }
    }


    // 토큰에서 만료 시간 가져오기
    private static Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(decodeKey).build().parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }

    // 토큰이 만료되었는지 확인
    public static boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }


    public static boolean isAccessTokenValid(String accessToken) {
        try {
            System.out.println("토큰이 정상적입니다.");
            return isTokenExpired(accessToken);
        } catch (ExpiredJwtException e) {
            // AccessToken이 만료된 경우
            System.out.println("AccessToken이 만료되었습니다.");
            return false;
        } catch (MalformedJwtException | SignatureException e) {
            // 유효하지 않은 형식의 토큰 또는 서명 오류가 발생한 경우
            System.out.println("유효하지 않은 AccessToken입니다.");
            return false;
        } catch (Exception e) {
            // 그 외 예외 처리
            e.printStackTrace();
            return false;
        }
    }


}


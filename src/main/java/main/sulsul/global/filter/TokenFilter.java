package main.sulsul.global.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * TODO: 향후 필터 방식으로 전환해볼 예정
 */
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT Token Filter Start!");

        String path = request.getRequestURI();
        filterChain.doFilter(request, response);
    }

//    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
//        String headerStr = request.getHeader("Authorization");
//
//        if (headerStr == null || headerStr.length() < 8) {
//            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
//        }
//
//        String tokenType = headerStr.substring(0, 6);
//        String tokenStr = headerStr.substring(7);
//
//        if (tokenType.equalsIgnoreCase("Bearer") == false) {
//            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
//        }
//
//        try {
//            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
//
//            return values;
//        } catch (MalformedJwtException malformedJwtException) {
//            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
//        } catch (SignatureException signatureException) {
//            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
//        } catch (ExpiredJwtException expiredJwtException) {
//            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
//        }
//    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request) {

        try (Reader reader = new InputStreamReader(request.getInputStream())) {

            Gson gson = new Gson();

            return gson.fromJson(reader, Map.class);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}

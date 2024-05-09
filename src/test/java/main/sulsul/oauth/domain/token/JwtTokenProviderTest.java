package main.sulsul.oauth.domain.token;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JwtTokenProviderTest {

    private static final String secretKey = "testSecretKey20230327testSecretKey20230327testSecretKey202303271221122121212122121212112212121221212112211212121212121221";
    private static final long accessTokenExpiration = 360000;
    private static final long refreshTokenExpiration = 604800000;


    JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
        secretKey,
        accessTokenExpiration,
        refreshTokenExpiration
    );

    @Test
    @DisplayName("토큰을 생성해 보자")
    void testGenerateAccessToken() {
        final LocalDate localDate = LocalDate.now().plusDays(20);

        final String token = jwtTokenProvider.generateAccessToken("1");
        assertThat(token).isNotBlank();
    }

    @Test
    @DisplayName("유효한 토큰 파싱")
    void testParseClaims() {
        final LocalDate localDate = LocalDate.now().plusDays(20);

        final String token = jwtTokenProvider.generateAccessToken("1");

        final Claims claims = jwtTokenProvider.parseClaims(token);
        assertThat(claims.getSubject()).isEqualTo("1");
    }

    @Test
    @DisplayName("만료 시간 지난 토큰 파싱하면 ExpiredJwtException 발생")
    void testParseClaimsOverExp() {
        final LocalDate localDate = LocalDate.now();

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
            secretKey,
            0L,
            refreshTokenExpiration
        );

        final String token = jwtTokenProvider.generateAccessToken("1");

        assertThatThrownBy(() -> jwtTokenProvider.parseClaims(token))
            .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void extractSubject() {
        final LocalDate localDate = LocalDate.now().plusDays(20);

        final String token = jwtTokenProvider.generateAccessToken("1");

        final String extractSubject = jwtTokenProvider.extractSubject(token);
        assertThat(extractSubject).isEqualTo("1");
    }

    @ParameterizedTest
    @MethodSource("methodSourceTestArguments")
    void beforeTest(int plusDays, boolean result) {
        final LocalDate localDate = LocalDate.now().plusDays(plusDays);

        final String token = jwtTokenProvider.generateAccessToken("1");
        final Claims claims = jwtTokenProvider.parseClaims(token);
        final Date expirationDate = claims.getExpiration();
        final boolean before = expirationDate.before(new Date());

        assertThat(before).isEqualTo(result);
    }

    private static Stream<Arguments> methodSourceTestArguments() {
        return Stream.of(
            Arguments.arguments(1, false),
            Arguments.arguments(20, false)
        );
    }

    @Test
    void dateTest() {
        final LocalDate localDate = LocalDate.now().plusDays(0);
        final Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        System.out.println(date.before(new Date()));
    }
}

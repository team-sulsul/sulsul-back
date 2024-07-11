package main.sulsul.oauth.domain.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.oauth.dto.AuthTokensResponse;
import main.sulsul.oauth.exception.OAuthErrorCode;
import main.sulsul.oauth.exception.OAuthException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokensGenerator {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final TokenValidator tokenValidator;

    @Transactional
    public AuthTokensResponse generate(Long memberId) {
        String subject = memberId.toString();
        String accessToken = jwtTokenProvider.generateAccessToken(subject);
        String refreshToken = jwtTokenProvider.generateRefreshToken(subject);

        Optional<Member> newMember = memberRepository.findById(memberId);

        newMember.ifPresent(member -> {
            member.setRefreshToken(refreshToken);
            log.info("Updated RefreshToken MemberId: {} ", member.getId());
        });
        return AuthTokensResponse.of(accessToken);
    }

    public String generateAccessToken(Long memberId) {
        String subject = memberId.toString();
        return jwtTokenProvider.generateAccessToken(subject);
    }

    public Long extractMemberId(HttpServletRequest request) {
        try {
            String accessToken = request.getHeader("Authorization").substring(7);
            return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
        } catch (ExpiredJwtException e) {
            tokenValidator.handleExpiredAccessToken(e);
        } catch (SignatureException | MalformedJwtException e) {
            throw new OAuthException(OAuthErrorCode.TOKEN_INVALID);
        }
        return null;
    }
}

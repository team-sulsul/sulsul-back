package main.sulsul.oauth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.Role;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.oauth.domain.generator.AuthTokens;
import main.sulsul.oauth.domain.generator.AuthTokensGenerator;
import main.sulsul.oauth.domain.oauth.OAuthInfoResponse;
import main.sulsul.oauth.domain.oauth.OAuthLoginParams;
import main.sulsul.oauth.domain.oauth.RequestOAuthInfoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        log.info("memberID : {}", memberId);
        return authTokensGenerator.generate(memberId);
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
                .password("1111")
                .build();
        return memberRepository.save(member).getId();
    }
}

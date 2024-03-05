package main.sulsul.authentication.service;

import lombok.RequiredArgsConstructor;
import main.sulsul.authentication.domain.AuthTokens;
import main.sulsul.authentication.domain.AuthTokensGenerator;
import main.sulsul.authentication.domain.Member;
import main.sulsul.authentication.domain.MemberRepository;
import main.sulsul.authentication.domain.oauth.OAuthInfoResponse;
import main.sulsul.authentication.domain.oauth.OAuthLoginParams;
import main.sulsul.authentication.domain.oauth.RequestOAuthInfoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
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
                .nickname(oAuthInfoResponse.getNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();
        return memberRepository.save(member).getId();
    }
}

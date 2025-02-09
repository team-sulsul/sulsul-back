package main.sulsul.oauth.application;

import jakarta.transaction.Transactional;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.Role;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.oauth.domain.kakao.KakaoLoginParams;
import main.sulsul.oauth.domain.oauth.OAuthInfoResponse;
import main.sulsul.oauth.domain.oauth.OAuthProvider;
import main.sulsul.oauth.domain.oauth.RequestOAuthInfoService;
import main.sulsul.oauth.domain.token.JwtTokenProvider;
import main.sulsul.oauth.dto.AuthTokensResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static main.sulsul.oauth.domain.oauth.OAuthProvider.KAKAO;
import static org.mockito.Mockito.when;

@SpringBootTest
class OAuthLoginServiceTest {

    @Autowired
    private OAuthLoginService oAuthLoginService;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private RequestOAuthInfoService requestOAuthInfoService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("회원 등록을 시도한다.")
//    @Transactional
    void registerMemberTest() {
        KakaoLoginParams params = new KakaoLoginParams();
        when(requestOAuthInfoService.request(params))
                .thenReturn(new OAuthInfoResponse() {
                    @Override
                    public String getEmail() {
                        return "somefood@naver.com";
                    }

                    @Override
                    public String getNickname() {
                        return "홍석주";
                    }

                    @Override
                    public OAuthProvider getOAuthProvider() {
                        return KAKAO;
                    }
                });

        AuthTokensResponse authTokensResponse = oAuthLoginService.registerMember(params);

        String id = jwtTokenProvider.extractSubject(authTokensResponse.getAccessToken());
        System.out.println("id = " + id);
    }

    @Test
    @DisplayName("탈퇴 기록이 있으면 새로 회원 등록을 시도한다.")
    @Transactional
    void registerAlreadyWithdrawMemberTest() {
        // given
        Member member = Member.builder()
                .username("withdraw@naver.com")
                .nickname("탈퇴 회원")
                .role(Role.USER)
                .password("1111")
                .useYn("N")
                .build();

        memberRepository.save(member);

        KakaoLoginParams params = new KakaoLoginParams();
        when(requestOAuthInfoService.request(params))
                .thenReturn(new OAuthInfoResponse() {
                    @Override
                    public String getEmail() {
                        return "withdraw@naver.com";
                    }

                    @Override
                    public String getNickname() {
                        return "탈퇴 후 새로 가입";
                    }

                    @Override
                    public OAuthProvider getOAuthProvider() {
                        return KAKAO;
                    }
                });

        oAuthLoginService.registerMember(params);
    }
}
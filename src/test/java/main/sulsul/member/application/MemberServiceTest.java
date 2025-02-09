package main.sulsul.member.application;

import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.Role;
import main.sulsul.member.domain.dao.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 탈퇴를 시도한다.")
    void withdrawMemberTest() {
        Member member = Member.builder()
                .username("withdraw@naver.com")
                .nickname("탈퇴 회원")
                .role(Role.USER)
                .password("1111")
                .build();

        memberRepository.save(member);

        member.withdraw();
        memberRepository.save(member);  // 변경사항 명시적으로 저장

        assertThat(member.isLive()).isFalse();
    }
}
package main.sulsul.member.application;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.MemberDto;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.oauth.domain.generator.AuthTokensGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;


    public MemberDto findByAccessToken(HttpServletRequest request) {
        MemberDto memberDto = new MemberDto();
        try {
            String accessToken = request.getHeader("Authorization").substring(7);
            Long memberId = authTokensGenerator.extractMemberId(accessToken);
            Member member = memberRepository.findById(memberId).get();
            memberDto.setRefreshToken(member.getRefreshToken());
            memberDto.setId(memberId);
            memberDto.setRole(member.getRole());
            memberDto.setNickname(member.getNickname());
            memberDto.setUsername(member.getUsername());
            return memberDto;
        } catch (IllegalArgumentException e) {
            memberDto.setMessage("유효하지 않은 회원입니다.");
            return memberDto;
        }
    }
}

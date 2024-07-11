package main.sulsul.member.ui;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.member.application.MemberService;
import main.sulsul.member.domain.MemberDto;
import main.sulsul.member.domain.MypageInfo;
import main.sulsul.oauth.domain.token.JwtTokensGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;
    private final JwtTokensGenerator jwtTokensGenerator;

    @GetMapping
    public CommonResponse<MemberDto> findByAccessToken(HttpServletRequest request) {
        Long memberId = jwtTokensGenerator.extractMemberId(request);
        return CommonResponse.ok(memberService.findByMemberId(memberId));
    }

    @GetMapping("/myPageInfo")
    public CommonResponse<MypageInfo> myPageInfo(HttpServletRequest request) {
        Long memberId = jwtTokensGenerator.extractMemberId(request);
        return CommonResponse.ok(memberService.myPageInfo(memberId));
    }

    /**
     * 회원 탈퇴
     *
     * @param request
     * @return
     */
    @PostMapping("/withdraw")
    public CommonResponse<Void> withdraw(HttpServletRequest request) {
        Long memberId = jwtTokensGenerator.extractMemberId(request);
        memberService.withdraw(memberId);
        return CommonResponse.ok(null);
    }
}

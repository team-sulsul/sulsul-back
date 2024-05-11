package main.sulsul.oauth.ui;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.member.application.MemberService;
import main.sulsul.member.domain.MemberDto;
import main.sulsul.member.domain.MypageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public CommonResponse<MemberDto> findByAccessToken(HttpServletRequest request) {
        return CommonResponse.ok(memberService.findByAccessToken(request));
    }

    @GetMapping("/{id}")
    public CommonResponse<MypageInfo> myPageInfo(@PathVariable Long id) {
        return CommonResponse.ok(memberService.myPageInfo(id));
    }
}

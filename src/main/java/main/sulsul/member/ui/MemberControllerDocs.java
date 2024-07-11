package main.sulsul.member.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.member.domain.MemberDto;
import main.sulsul.member.domain.MypageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Member API")
public interface MemberControllerDocs {

    @GetMapping
    CommonResponse<MemberDto> findByAccessToken(HttpServletRequest request);

    @GetMapping("/myPageInfo")
    CommonResponse<MypageInfo> myPageInfo(HttpServletRequest request);

    /**
     * 회원 탈퇴
     * @param request
     * @return
     */
    @Operation(summary = "탈퇴하기", description = "탈퇴하기")
    @PostMapping("/withdraw")
    CommonResponse<Void> withdraw(HttpServletRequest request);
}

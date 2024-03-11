package main.sulsul.admin.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.admin.application.AdminMemberService;
import main.sulsul.admin.dto.PageRequest;
import main.sulsul.admin.dto.PageResultDto;
import main.sulsul.admin.dto.RecordResponse;
import main.sulsul.beverage.domain.Record;
import main.sulsul.member.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final AdminMemberService memberService;

    @GetMapping("/admin/members")
    public String getMemberList(Model model) {
        List<Member> members = memberService.getMemberList();
        model.addAttribute("members", members);
        return "pages/members/home";
    }

    @GetMapping("/admin/members/{memberId}")
    public String getMember(@PathVariable("memberId") Long memberId, Model model) {
        final Member member = memberService.getMember(memberId);
        model.addAttribute("member", member);
        model.addAttribute("memberId", memberId);
        return "pages/members/detail";
    }

    @ResponseBody
    @GetMapping("/admin/members/{memberId}/records")
    public PageResultDto<Record, RecordResponse> getRecordByMember(@PathVariable("memberId") Long memberId, @ModelAttribute PageRequest pageRequest) {
        return memberService.getRecords(memberId, pageRequest);
    }

}

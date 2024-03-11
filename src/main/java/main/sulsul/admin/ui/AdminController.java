package main.sulsul.admin.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.admin.application.AdminService;
import main.sulsul.member.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/admins")
    public String getAdminList(Model model) {
        List<Member> members = adminService.getAdminList();
        model.addAttribute("members", members);
        return "pages/members/home";
    }

    @GetMapping("/admin/admins/{adminId}")
    public String getAdmin(@PathVariable("adminId") Long adminId, Model model) {
        final Member member = adminService.getAdmin(adminId);
        model.addAttribute("member", member);
        return "pages/members/detail";
    }
}

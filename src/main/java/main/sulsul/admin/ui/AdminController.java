package main.sulsul.admin.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.admin.application.AdminService;
import main.sulsul.admin.dto.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/login")
    public String getLoginPage() {
        return "pages/admin/login";
    }

    @PostMapping("/admin/login")
    public String doLogin(LoginRequest loginRequest, Model model) {
        log.info("로그인 시도 {}", loginRequest);
        adminService.login(loginRequest);

        return "redirect:/admin/home";
    }

    @GetMapping("/admin/home")
    public String home() {

        return "pages/admin/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.do";
    }
}

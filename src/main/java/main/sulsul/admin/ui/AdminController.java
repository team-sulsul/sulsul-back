package main.sulsul.admin.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.admin.dto.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    @GetMapping("/admin/login")
    public String getLoginPage() {
        return "pages/admin/login";
    }

    @PostMapping("/admin/login")
    public String doLogin(LoginRequest loginRequest) {
        log.info("로그인 시도 {}", loginRequest);
        return "";
    }
}

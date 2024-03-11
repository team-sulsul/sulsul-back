package main.sulsul.admin.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminMainController {

    @GetMapping("/admin/login")
    public String getLoginPage() {
        return "pages/admin/login";
    }

    @GetMapping("/admin/home")
    public String home() {
        return "pages/admin/home";
    }

    @GetMapping("/admin/error")
    public String error() {
        return "pages/admin/error";
    }
}

package main.sulsul;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/admin/login")
    public String greeting() {
        return "pages/test/testPage";
    }

    @GetMapping("/hello")
    public String greeting2() {
        return "pages/test/testPage";
    }
}

package main.sulsul.admin.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.admin.application.AdminBeverageService;
import main.sulsul.admin.dto.BeverageEditForm;
import main.sulsul.admin.dto.BeverageSearch;
import main.sulsul.beverage.domain.Beverage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminBeverageController {

    private final AdminBeverageService beverageService;

    @GetMapping("/admin/beverages")
    public String getBeverageList(@ModelAttribute BeverageSearch beverageSearch, Model model) {
        List<Beverage> beverages = beverageService.getBeverageList(beverageSearch);
        model.addAttribute("beverages", beverages);

        return "pages/beverages/home";
    }

    @GetMapping("/admin/beverages/{beverage}")
    public String getBeverage(@PathVariable("beverage") Beverage beverage, Model model) {
        model.addAttribute("beverage", beverage);
        return "pages/beverages/detail";
    }
}

package main.sulsul.admin.ui;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.admin.application.AdminBeverageService;
import main.sulsul.admin.dto.BeverageEditForm;
import main.sulsul.admin.dto.BeverageSearch;
import main.sulsul.beverage.domain.Beverage;
import main.sulsul.beverage.domain.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminBeverageController {

    private final AdminBeverageService beverageService;

    @GetMapping("/admin/beverages")
    public String getBeverageList(@ModelAttribute BeverageSearch beverageSearch, Model model) {
        List<Beverage> beverages = beverageService.getBeverageList(beverageSearch);
        model.addAttribute("beverages", beverages);

        final Category[] categories = Category.values();
        model.addAttribute("categories", categories);
        return "pages/beverages/home";
    }

    @GetMapping("/admin/beverages/{beverageId}")
    public String getBeverage(@PathVariable("beverageId") Long beverageId, Model model) {
        final Beverage beverage = beverageService.getBeverage(beverageId);
        model.addAttribute("beverage", beverage);
        return "pages/beverages/detail";
    }

    @GetMapping("/admin/beverages/{beverageId}/edit")
    public String getBeverageForEdit(@PathVariable("beverageId") Long beverageId, Model model) {
        final Beverage beverage = beverageService.getBeverage(beverageId);
        model.addAttribute("beverage", beverage);
        final Category[] categories = Category.values();
        model.addAttribute("categories", categories);
        return "pages/beverages/edit";
    }

    @PostMapping("/admin/beverages/{beverageId}/edit")
    public String editBeverage(@PathVariable("beverageId") Long beverageId, @ModelAttribute BeverageEditForm editForm) {
        beverageService.editBeverage(beverageId, editForm);
        return "redirect:/admin/beverages/" + beverageId;
    }
}

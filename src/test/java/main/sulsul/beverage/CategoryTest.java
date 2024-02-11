package main.sulsul.beverage;

import main.sulsul.beverage.domain.Category;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void showAllCategory() {
        Category root = Category.ROOT;
        System.out.println(root.getChildCategories());
    }
}

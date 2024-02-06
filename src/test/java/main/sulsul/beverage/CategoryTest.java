package main.sulsul.beverage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void showAllCategory() {
        Category root = Category.ROOT;
        System.out.println(root.getChildCategories());
    }
}

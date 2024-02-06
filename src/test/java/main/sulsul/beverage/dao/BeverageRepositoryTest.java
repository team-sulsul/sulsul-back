package main.sulsul.beverage.dao;

import main.sulsul.beverage.Beverage;
import main.sulsul.beverage.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeverageRepositoryTest {

    @Autowired
    private BeverageRepository beverageRepository;

    @Test
    void getBeverage() {
        Optional<Beverage> findBeverage = beverageRepository.findById(1L);

        if (findBeverage.isPresent()) {
            Category category = findBeverage.get().getCategory();
            System.out.println(category);
        }
    }
}

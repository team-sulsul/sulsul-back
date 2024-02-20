package main.sulsul.beverage.dao;

import main.sulsul.beverage.domain.Beverage;
import main.sulsul.beverage.domain.Category;
import main.sulsul.beverage.domain.dao.BeverageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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

package main.sulsul.beverage.dao;

import main.sulsul.beverage.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {
}

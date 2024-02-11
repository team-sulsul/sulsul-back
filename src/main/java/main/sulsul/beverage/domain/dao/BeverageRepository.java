package main.sulsul.beverage.domain.dao;

import main.sulsul.beverage.domain.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {
}

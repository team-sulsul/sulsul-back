package main.sulsul.admin.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.admin.dto.BeverageEditForm;
import main.sulsul.admin.dto.BeverageSearch;
import main.sulsul.beverage.domain.Beverage;
import main.sulsul.beverage.domain.dao.BeverageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminBeverageService {

    private final BeverageRepository beverageRepository;

    public List<Beverage> getBeverageList(BeverageSearch beverageSearch) {
        log.info("{}", beverageSearch);
        return beverageRepository.findAll();
    }

    public Beverage getBeverage(Long beverageId) {
        return beverageRepository.findById(beverageId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 술"));
    }

    @Transactional
    public void editBeverage(Long beverageId, BeverageEditForm editForm) {
        final Beverage beverage = beverageRepository.findById(beverageId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 술"));

        beverage.update(editForm);
    }
}

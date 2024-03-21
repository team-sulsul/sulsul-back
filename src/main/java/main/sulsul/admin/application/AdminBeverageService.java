package main.sulsul.admin.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.admin.dto.BeverageEditForm;
import main.sulsul.admin.dto.BeverageSearch;
import main.sulsul.beverage.domain.Beverage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminBeverageService {

    public List<Beverage> getBeverageList(BeverageSearch beverageSearch) {
        log.info("{}", beverageSearch);
        return List.of(Beverage.values());
    }

    @Transactional
    public void editBeverage(Long beverageId, BeverageEditForm editForm) {
//        final Beverage beverage = beverageRepository.findById(beverageId)
//            .orElseThrow(() -> new RuntimeException("존재하지 않는 술"));
//
//        beverage.update(editForm);
    }
}

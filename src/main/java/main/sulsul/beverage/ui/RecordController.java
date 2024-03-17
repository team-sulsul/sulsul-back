package main.sulsul.beverage.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.beverage.dto.BeverageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RecordController {

    @PostMapping("/records")
    public String createRecord(@RequestBody List<BeverageRequest> beverageRequests) {
        log.info("beverageRequest: {}", beverageRequests);
        return "hello";
    }
}

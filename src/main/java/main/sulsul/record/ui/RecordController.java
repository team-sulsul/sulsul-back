package main.sulsul.record.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.record.application.RecordService;
import main.sulsul.record.dto.RecordBeverageRequest;
import main.sulsul.record.dto.RecordDrunkenLevelRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RecordController {

    private final RecordService recordService;

    @PostMapping("/records/step1")
    public ResponseEntity<Long> recordBeverages(@RequestBody RecordBeverageRequest recordBeverageRequest) {
        final Long resultId = recordService.recordBeverages(1L, recordBeverageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultId);
    }

    @PostMapping("/records/step2")
    public ResponseEntity<Void> recordDrunkenLevel(@RequestBody RecordDrunkenLevelRequest recordDrunkenLevelRequest) {
        recordService.recordDrunkenLevel(1L, recordDrunkenLevelRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}

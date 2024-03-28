package main.sulsul.record.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.record.application.RecordService;
import main.sulsul.record.dto.RecordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RecordController {

    private final RecordService recordService;

    @PostMapping("/records")
    public ResponseEntity<Void> recordBeverages(@RequestBody RecordRequest recordRequest) {
        log.info("{}", recordRequest);
        recordService.recordBeverages(1L, recordRequest);
        return ResponseEntity.ok(null);
    }
}

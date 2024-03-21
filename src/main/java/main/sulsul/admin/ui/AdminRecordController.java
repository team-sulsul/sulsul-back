package main.sulsul.admin.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.admin.application.AdminRecordService;
import main.sulsul.record.domain.RecordBeverage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminRecordController {

    private final AdminRecordService recordService;

    @ResponseBody
    @GetMapping("/admin/records/{recordId}")
    public ResponseEntity<List<RecordBeverage>> getRecordDetail(@PathVariable("recordId") Long recordId) {
        final List<RecordBeverage> result = recordService.getRecordDetail(recordId);
        return ResponseEntity.ok(result);
    }
}

package main.sulsul.record.ui;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.oauth.domain.generator.AuthTokensGenerator;
import main.sulsul.record.application.RecordService;
import main.sulsul.record.dto.RecordBeverageRequest;
import main.sulsul.record.dto.RecordBulkRequest;
import main.sulsul.record.dto.RecordDrunkenLevelRequest;
import main.sulsul.record.dto.response.CalendarResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RecordController {

    private final RecordService recordService;
    private final AuthTokensGenerator authTokensGenerator;

    @GetMapping("/records")
    public CommonResponse<List<CalendarResponse>> getRecords(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        Long memberId = authTokensGenerator.extractMemberId(accessToken);
        final List<CalendarResponse> records = recordService.getRecords(memberId);
        return CommonResponse.ok(records);
    }

    @PostMapping("/records/step1")
    public CommonResponse<Long> recordBeverages(HttpServletRequest request, @RequestBody RecordBeverageRequest recordBeverageRequest) {
        String accessToken = request.getHeader("Authorization");
        Long memberId = authTokensGenerator.extractMemberId(accessToken);

        final Long resultId = recordService.recordBeverages(memberId, recordBeverageRequest);
        return CommonResponse.ok(resultId);
    }

    @PostMapping("/records/step2")
    public CommonResponse<Void> recordDrunkenLevel(HttpServletRequest request, @RequestBody RecordDrunkenLevelRequest recordDrunkenLevelRequest) {
        String accessToken = request.getHeader("Authorization");
        Long memberId = authTokensGenerator.extractMemberId(accessToken);

        recordService.recordDrunkenLevel(memberId, recordDrunkenLevelRequest);
        return CommonResponse.ok(null);
    }

    @PostMapping("/records/bulk")
    public CommonResponse<Void> recordBulk(HttpServletRequest request, @RequestBody List<RecordBulkRequest> recordBulkRequests) {
        String accessToken = request.getHeader("Authorization");
        Long memberId = authTokensGenerator.extractMemberId(accessToken);

        recordService.recordBulk(memberId, recordBulkRequests);
        return CommonResponse.ok(null);
    }
}

package main.sulsul.record.ui;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.oauth.domain.token.TokenValidator;
import main.sulsul.record.application.RecordService;
import main.sulsul.record.dto.*;
import main.sulsul.record.dto.response.CalendarResponse;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RecordController implements RecordControllerDocs {

    private final RecordService recordService;
    private final TokenValidator tokenValidator;

    @GetMapping("/records")
    public CommonResponse<List<CalendarResponse>> getRecords(HttpServletRequest request) {
        Long memberId = tokenValidator.extractMemberId(request);
        final List<CalendarResponse> records = recordService.getRecords(memberId);
        return CommonResponse.ok(records);
    }

    @PostMapping("/records/step1")
    public CommonResponse<Long> recordBeverages(HttpServletRequest request, @RequestBody RecordBeverageRequest recordBeverageRequest) {
        Long memberId = tokenValidator.extractMemberId(request);

        final Long resultId = recordService.recordBeverages(memberId, recordBeverageRequest);
        return CommonResponse.ok(resultId);
    }

    @PutMapping("/records/step1")
    public CommonResponse<Void> modifyBeverages(HttpServletRequest request,
                                                @RequestBody RecordBeverageModifyRequest modifyRequest) {
        final Long memberId = tokenValidator.extractMemberId(request);

        recordService.modifyBeverages(memberId, modifyRequest);
        return CommonResponse.ok(null);
    }

    @PostMapping("/records/step2")
    public CommonResponse<Void> recordDrunkenLevel(HttpServletRequest request, @Valid @RequestBody RecordDrunkenLevelRequest recordDrunkenLevelRequest) {
        Long memberId = tokenValidator.extractMemberId(request);

        recordService.recordDrunkenLevel(memberId, recordDrunkenLevelRequest);
        return CommonResponse.ok(null);
    }

    @DeleteMapping("/records")
    public CommonResponse<Void> deleteRecord(HttpServletRequest request, @RequestBody RecordDeleteRequest recordDeleteRequest) {
        Long memberId = tokenValidator.extractMemberId(request);

        recordService.deleteRecord(memberId, recordDeleteRequest);
        return CommonResponse.ok(null);
    }

    @PostMapping("/records/bulk")
    public CommonResponse<Void> recordBulk(HttpServletRequest request, @RequestBody List<RecordBulkRequest> recordBulkRequests) {
        Long memberId = tokenValidator.extractMemberId(request);

        recordService.recordBulk(memberId, recordBulkRequests);
        return CommonResponse.ok(null);
    }
}

package main.sulsul.record.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.record.dto.*;
import main.sulsul.record.dto.response.CalendarResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "음주 기록 API")
public interface RecordControllerDocs {

    @GetMapping("/records")
    @Operation(summary = "전체 음주 기록 조회", description = "전체 음주 기록 조회")
    CommonResponse<List<CalendarResponse>> getRecords(HttpServletRequest request);

    @PostMapping("/records/step1")
    @Operation(summary = "step1 - 음주 정보 기록", description = "step1 - 음주 정보 기록")
    CommonResponse<Long> recordBeverages(HttpServletRequest request, @RequestBody RecordBeverageRequest recordBeverageRequest);

    @PutMapping("/records/step1")
    @Operation(summary = "step1 - 음주 정보 수정", description = "step1 - 음주 정보 수정")
    CommonResponse<Void> modifyBeverages(HttpServletRequest request, @RequestBody RecordBeverageModifyRequest modifyRequest);

    @PostMapping("/records/step2")
    @Operation(summary = "step2 - 취함 레벨 설정", description = "step2 - 취함 레벨 설정")
    CommonResponse<Void> recordDrunkenLevel(HttpServletRequest request, @Valid @RequestBody RecordDrunkenLevelRequest recordDrunkenLevelRequest);

    @DeleteMapping("/records")
    @Operation(summary = "음주 기록 삭제", description = "음주 기록 삭제")
    CommonResponse<Void> deleteRecord(HttpServletRequest request, @RequestBody RecordDeleteRequest recordDeleteRequest);

    @PostMapping("/records/bulk")
    @Operation(summary = "벌크 기록 INSERT", description = "벌크 기록 INSERT")
    CommonResponse<Void> recordBulk(HttpServletRequest request, @RequestBody List<RecordBulkRequest> recordBulkRequests);
}

package main.sulsul.statistics.ui;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.oauth.domain.generator.AuthTokensGenerator;
import main.sulsul.statistics.application.StatisticsService;
import main.sulsul.statistics.dto.TotalStatisticsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StatisticsController {

    private final AuthTokensGenerator authTokensGenerator;
    private final StatisticsService statisticsService;

    @GetMapping("/statistics")
    public CommonResponse<TotalStatisticsResponse> statistics(HttpServletRequest request, @RequestParam(value = "startDate", defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate startDate) {
        final String accessToken = request.getHeader("Authorization");
        final Long memberId = authTokensGenerator.extractMemberId(accessToken);

        final TotalStatisticsResponse result = statisticsService.getStatistics(memberId, startDate);
        return CommonResponse.ok(result);
    }
}

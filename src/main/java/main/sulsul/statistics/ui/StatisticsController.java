package main.sulsul.statistics.ui;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import main.sulsul.oauth.domain.generator.AuthTokensGenerator;
import main.sulsul.statistics.application.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StatisticsController {

    private final AuthTokensGenerator authTokensGenerator;
    private final StatisticsService statisticsService;

    @GetMapping
    public String statistics(HttpServletRequest request, @PathVariable("startDate") LocalDate startDate) {
        final String accessToken = request.getHeader("Authorization");
        final Long memberId = authTokensGenerator.extractMemberId(accessToken);

        statisticsService.getStatistics(memberId, startDate);
        return null;
    }
}

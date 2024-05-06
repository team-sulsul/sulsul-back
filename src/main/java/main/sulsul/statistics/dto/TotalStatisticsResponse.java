package main.sulsul.statistics.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TotalStatisticsResponse {

    private Section1 section1;

    private List<Section2> section2;

    private Section3 section3;

    public TotalStatisticsResponse(Section1 section1, List<Section2> section2, Section3 section3) {
        this.section1 = section1;
        this.section2 = section2;
        this.section3 = section3;
    }
}

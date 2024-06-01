package main.sulsul.statistics.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Section3 {

    private String maxDrunkenStatus;
    private Integer drunkenLevel1Count;
    private Integer drunkenLevel2Count;
    private Integer drunkenLevel3Count;
    private Integer drunkenLevel4Count;
    private Integer drunkenLevel5Count;

    public Section3(Integer drunkenLevel1Count, Integer drunkenLevel2Count, Integer drunkenLevel3Count,
                    Integer drunkenLevel4Count, Integer drunkenLevel5Count) {
        this.drunkenLevel1Count = drunkenLevel1Count;
        this.drunkenLevel2Count = drunkenLevel2Count;
        this.drunkenLevel3Count = drunkenLevel3Count;
        this.drunkenLevel4Count = drunkenLevel4Count;
        this.drunkenLevel5Count = drunkenLevel5Count;
    }

    public void setMaxDrunkenStatus(String maxDrunkenStatus) {
        this.maxDrunkenStatus = maxDrunkenStatus;
    }
}

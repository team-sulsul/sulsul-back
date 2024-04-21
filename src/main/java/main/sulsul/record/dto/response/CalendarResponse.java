package main.sulsul.record.dto.response;

import java.time.LocalDate;
import java.util.List;
import main.sulsul.record.domain.DrunkenLevel;
import main.sulsul.record.dto.BeverageInfo;

public class CalendarResponse {

    private LocalDate recordedAt;

    private DrunkenLevel drunkenLevel;

    private List<BeverageInfo> beverages;
}

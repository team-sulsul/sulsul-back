package main.sulsul.record.domain.dao;

import java.util.List;
import main.sulsul.record.dto.response.CalendarResponse;

public interface RecordCustomRepository {

    List<CalendarResponse> getRecordBulk(Long memberId);
}

package main.sulsul.admin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RecordResponse {

    private final Long recordId;

    private final LocalDate recordedAt;

    private final String drunkenLevel;

    private final LocalDateTime createdAt;

    public RecordResponse(Long recordId, LocalDate recordedAt, String drunkenLevel, LocalDateTime createdAt) {
        this.recordId = recordId;
        this.recordedAt = recordedAt;
        this.drunkenLevel = drunkenLevel;
        this.createdAt = createdAt;
    }
}

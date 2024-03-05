package main.sulsul.admin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RecordResponse {

    private Long recordId;

    private LocalDate recordedAt;

    private String hangoverLevel;

    private LocalDateTime createdAt;

    public RecordResponse(Long recordId, LocalDate recordedAt, String hangoverLevel, LocalDateTime createdAt) {
        this.recordId = recordId;
        this.recordedAt = recordedAt;
        this.hangoverLevel = hangoverLevel;
        this.createdAt = createdAt;
    }
}

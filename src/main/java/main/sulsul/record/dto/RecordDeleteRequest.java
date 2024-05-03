package main.sulsul.record.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RecordDeleteRequest {

    private LocalDate recordedAt;
}

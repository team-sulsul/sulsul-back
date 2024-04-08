package main.sulsul.global.exception.dto;

import lombok.Getter;
import main.sulsul.global.exception.ErrorCode;
import org.springframework.http.ResponseEntity;

@Getter
public class CustomExceptionResponse {

    private final String code;
    private String errorMessage;

    public CustomExceptionResponse(String code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public static ResponseEntity<CustomExceptionResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(new CustomExceptionResponse(errorCode.getCode(), errorCode.getMessage()));
    }
}

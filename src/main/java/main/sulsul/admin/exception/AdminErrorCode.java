package main.sulsul.admin.exception;

import main.sulsul.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AdminErrorCode implements ErrorCode {
    RECORD_NOT_FOUND(HttpStatus.BAD_REQUEST, 400, "존재하지 않는 기록"),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, 400, "이미 존재하는 기록");

    private final HttpStatus httpStatus;
    private final Integer errorCode;
    private final String errorMessage;

    AdminErrorCode(HttpStatus httpStatus, Integer errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public Integer getCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}

package main.sulsul.oauth.exception;

import main.sulsul.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum OAuthErrorCode implements ErrorCode {
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, 601, "만료된 토큰"),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, 602, "유효하지 않은 토큰");

    private final HttpStatus httpStatus;
    private final Integer errorCode;
    private final String errorMessage;

    OAuthErrorCode(HttpStatus httpStatus, Integer errorCode, String errorMessage) {
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

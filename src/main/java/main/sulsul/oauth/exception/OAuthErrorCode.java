package main.sulsul.oauth.exception;

import main.sulsul.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum OAuthErrorCode implements ErrorCode {
    ACCESS_TOKEN_RENEWAL(HttpStatus.BAD_REQUEST, 400, "액세스 토큰 갱신"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, 401, "리프레쉬 토큰 만료\n재로그인 필요"),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, 402, "유효하지 않은 토큰");

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

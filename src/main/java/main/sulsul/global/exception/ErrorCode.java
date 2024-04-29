package main.sulsul.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();
    Integer getCode();
    String getMessage();
}

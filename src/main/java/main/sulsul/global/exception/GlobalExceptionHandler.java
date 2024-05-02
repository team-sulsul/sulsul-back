package main.sulsul.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.global.dto.CommonResponse;
import main.sulsul.oauth.exception.OAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_LOG_FORMAT = "url={}, errorCode={}, errorMessage={}";
    private static final String ERROR_LOG_FORMAT_WITH_WRONG_INPUT = "url={}, errorCode={}, errorMessage={}, errorInput={}";

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(OAuthException.class)
    public CommonResponse<Void> handleOAuthException(final OAuthException exception, final HttpServletRequest request) {
        trackLog(exception, request);
        return CommonResponse.businessError(exception);
    }

    @ExceptionHandler(CommonException.class)
    public CommonResponse<Void> handleException(final CommonException exception, final HttpServletRequest request) {
        trackLog(exception, request);
        return CommonResponse.businessError(exception);
    }

    private void trackLog(final CommonException exception, final HttpServletRequest request) {
        if (exception.isInputFieldsNUll()) {
            log.error(ERROR_LOG_FORMAT, request.getRequestURL(), exception.getCode(), exception.getMessage());
            return;
        }
        log.error(ERROR_LOG_FORMAT_WITH_WRONG_INPUT, request.getRequestURL(), exception.getCode(),
                  exception.getMessage(), exception.getInputValue());
    }
}

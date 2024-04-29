package main.sulsul.oauth.exception;

import java.util.Map;
import main.sulsul.global.exception.CommonException;
import main.sulsul.global.exception.ErrorCode;

public class OAuthException extends CommonException {

    public OAuthException(ErrorCode errorCode, Map<String, Object> additionalInfos) {
        super(errorCode, additionalInfos);
    }

    public OAuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}

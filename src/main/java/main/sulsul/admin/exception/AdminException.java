package main.sulsul.admin.exception;

import main.sulsul.global.exception.CommonException;
import main.sulsul.global.exception.ErrorCode;

import java.util.Map;

public class AdminException extends CommonException {

    public AdminException(ErrorCode errorCode, Map<String, Object> additionalInfos) {
        super(errorCode, additionalInfos);
    }

    public AdminException(ErrorCode errorCode) {
        super(errorCode);
    }
}

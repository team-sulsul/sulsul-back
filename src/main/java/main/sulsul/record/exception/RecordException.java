package main.sulsul.record.exception;

import java.util.Map;
import main.sulsul.global.exception.CommonException;
import main.sulsul.global.exception.ErrorCode;

public class RecordException extends CommonException {

    public RecordException(ErrorCode errorCode, Map<String, Object> additionalInfos) {
        super(errorCode, additionalInfos);
    }

    public RecordException(ErrorCode errorCode) {
        super(errorCode);
    }
}

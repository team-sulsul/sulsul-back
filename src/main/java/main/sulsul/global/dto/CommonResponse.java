package main.sulsul.global.dto;

import lombok.Getter;
import main.sulsul.global.exception.CommonException;

@Getter
public class CommonResponse<T> {

    private static final Integer SUCCESS_CODE = 200;
    private static final String SUCCESS_MESSAGE = "수행완료";


    private Integer resultCode;

    private String resultMessage;

    private T resultData;

    public CommonResponse(Integer resultCode, String resultMessage, T data) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.resultData = data;
    }

    public CommonResponse(Integer resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public static <T> CommonResponse<T> ok(T data) {
        return new CommonResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> CommonResponse<T> businessError(CommonException exception) {
        return new CommonResponse<>(exception.getCode(), exception.getMessage());
    }
}

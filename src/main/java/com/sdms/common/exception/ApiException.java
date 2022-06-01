package com.sdms.common.exception;

import com.sdms.common.result.LayuiResult;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final int code;
    private final String msg;

    public ApiException() {
        this(LayuiResult.ResultCode.FAILED);
    }

    public ApiException(LayuiResult.ResultCode failed) {
        this.code = failed.getCode();
        this.msg = failed.getMsg();
    }
}
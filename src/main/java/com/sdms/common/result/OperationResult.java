package com.sdms.common.result;

import lombok.Getter;
import lombok.Setter;

/**
 * OperationResult的本质是一种 DTO ,
 * service层执行写操作(包括 增、删、改)的方法统一返回OperationResult对象
 */
@Setter
@Getter
public class OperationResult<T> {
    private boolean success = false;
    private String msg = "";
    private T value = null;

    private static <T> OperationResult<T> newInstance(boolean success, T value, String msg) {
        OperationResult<T> result = new OperationResult<>();
        result.success = success;
        result.value = value;
        result.msg = msg;
        return result;
    }

    public static <T> OperationResult<T> success(T value) {
        return newInstance(true, value, "");
    }

    public static <T> OperationResult<T> failure(String msg) {
        return newInstance(false, null, msg);
    }

}

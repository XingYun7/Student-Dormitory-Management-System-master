package com.sdms.common.exception;

import com.sdms.common.result.LayuiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的异常 ApiException
     */
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public LayuiResult<String> ApiExceptionHandler(ApiException e) {
        log.error("api异常");
        List<String> msgList = new ArrayList<>();
        msgList.add(e.getMsg());
        return new LayuiResult<>(LayuiResult.ResultCode.FAILED, null, msgList);
    }

    /**
     * 处理方法参数错误的异常 MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public LayuiResult<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("方法参数错误异常");
        List<String> msgList = new ArrayList<>();
        if (!e.getBindingResult().getAllErrors().isEmpty()) {
            for (ObjectError error : e.getBindingResult().getAllErrors()) {
                msgList.add(error.getDefaultMessage());
            }
        }
        // 然后提取错误提示信息进行返回
        return new LayuiResult<>(LayuiResult.ResultCode.VALIDATE_FAILED, null, msgList);
    }
}

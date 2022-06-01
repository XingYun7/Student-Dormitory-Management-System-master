package com.sdms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdms.common.exception.ApiException;
import com.sdms.common.page.Page;
import com.sdms.common.result.LayuiResult;
import lombok.NonNull;
import lombok.val;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.*;

/**
 * 将JSON返回值统一包装成LayuiResult对象
 * 注：对使用了@ResponseBody的Restful接口有效
 */
@RestControllerAdvice(basePackages = {"com.sdms.controller"})
public class Advice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型已经是LayuiResult,则无需转换
        val returnTypeName = returnType.getGenericParameterType().getTypeName();
        val layuiResultTypeName = LayuiResult.class.getTypeName();
        return !returnTypeName.startsWith(layuiResultTypeName); // 前者包含泛型信息,后者不包含,所以用startsWith而不是equals
    }

    // String =>beforeBodyWrite(包装成LayuiResult,手动转json) => String;
    // 其它 =>beforeBodyWrite(包装成LayuiResult) => LayuiResult => springMVC自动转json => String;
    @Override
    public Object beforeBodyWrite(Object data, @NonNull MethodParameter returnType, @NonNull MediaType mediaType, @NonNull Class<? extends HttpMessageConverter<?>> aClass, @NonNull ServerHttpRequest serverHttpRequest, @NonNull ServerHttpResponse serverHttpResponse) {
        // 如果controller层中返回的类型是String,那么springMVC在选择处理MessageConverter时会选择StringMessageConverter。
        // 问题在于StringMessageConverter只接受String类型,不能处理包装后的LayuiResult类型,所以要转成json字符串作为返回值
        if (data instanceof String) {
            // String类型
            val objectMapper = new ObjectMapper();
            try {
                // 将数据包装在LayuiResult里后，再转换为json字符串响应给前端
                val result = new LayuiResult<>(LayuiResult.ResultCode.SUCCESS, null, Collections.singletonList((String) data));
                return objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                throw new ApiException();
            }
        } else if (data instanceof Page) {
            // Page类型
            return new LayuiResult<>((Page<?>) data);
        } else {
            // 其它的对象类型
            List<Object> objectList;
            if (data == null) {
                objectList = Collections.emptyList();
            } else {
                objectList = Collections.singletonList(data);
            }
            return new LayuiResult<>(LayuiResult.ResultCode.SUCCESS, null, objectList);
        }
    }
}
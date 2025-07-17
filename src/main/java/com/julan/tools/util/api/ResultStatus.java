package com.julan.tools.util.api;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Component
@ControllerAdvice
public class ResultStatus implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //处理JSON格式响应
        if (selectedContentType.equalsTypeAndSubtype(MediaType.APPLICATION_JSON)) {
            //获取响应状态码
            int statusCode = (int) ((ResultJson<?>) body).getCode();
            //绑定响应状态码到响应码
            response.setStatusCode(HttpStatus.valueOf(statusCode));
        }
        return body;
    }
}

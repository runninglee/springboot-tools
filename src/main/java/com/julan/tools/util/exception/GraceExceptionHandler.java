package com.julan.tools.util.exception;


import com.julan.tools.util.api.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GraceExceptionHandler {

    @ExceptionHandler(GraceException.class)
    public ResultJson<Object> handler(GraceException e) {
        return ResultJson.failed(e.getMessage(), e.code);
    }

    //请求参数缺失或错误异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultJson<Object> handleUnreadable(HttpMessageNotReadableException e) {
        log.info("请求参数异常: {}", e.getMessage());
        return ResultJson.failed("请求体缺失或格式错误，请传入合法参数");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson<Object> handler(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = errors.isEmpty()
                ? "参数校验失败"
                : errors.getFirst().getDefaultMessage(); // ✅ 获取注解中的 message，例如“身份证号已经存在”
        return ResultJson.validateFailed(message);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultJson<Object> handler(NoHandlerFoundException e) {
        return ResultJson.failed("请求路由不存在", 404);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResultJson<Object> handler(NoResourceFoundException e) {
        return ResultJson.failed("请求路由不存在", 404);
    }

    //数据库主键重复异常
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResultJson<Object> handler(DataIntegrityViolationException e) {
        if (e.getMessage().contains("Duplicate entry")) {
            return ResultJson.failed("数据重复,禁止重复添加");
        } else {
            return ResultJson.failed(e.getMessage());
        }
    }

    @ExceptionHandler(Throwable.class)
    public ResultJson<Object> handler(Exception e) {
        return ResultJson.failed(e.getMessage());
    }
}

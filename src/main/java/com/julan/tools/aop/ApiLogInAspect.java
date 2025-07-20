package com.julan.tools.aop;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julan.tools.request.apiLog.CreateApiLogRequest;
import com.julan.tools.service.ApiLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Project : tools
 * @Package : com.julan.tools.aop
 * @ClassName : ApiLogAspect
 * @Description : Api Log AOP
 * @Author :  Hui Lee
 * @CreateTime : 2025/7/18 13:46
 * @Version : V1.0.0
 */
@Aspect
@Component
@Slf4j
public class ApiLogInAspect {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Resource
    protected ApiLogService apiLogService;

    @Around("@annotation(apiLogIn)")
    public Object logAround(ProceedingJoinPoint joinPoint, ApiLogIn apiLogIn) throws Throwable {
        if (apiLogIn.enabled()) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method targetMethod = signature.getMethod();
            ApiLogIn apiLog = targetMethod.getAnnotation(ApiLogIn.class);
            // 请求参数
            Object[] args = joinPoint.getArgs();
            String params = Arrays.stream(args).filter(arg -> !(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)).map(arg -> {
                try {
                    return OBJECT_MAPPER.writeValueAsString(arg);
                } catch (JsonProcessingException e) {
                    return String.valueOf(arg);
                }
            }).collect(Collectors.joining(", "));

            CreateApiLogRequest createApiLogRequest = new CreateApiLogRequest();
            //类型
            createApiLogRequest.setCategory(apiLog.category());
            //任务类型
            createApiLogRequest.setTask(apiLog.task());
            //接口名称
            createApiLogRequest.setName(apiLog.value());
            //关联对象信息
            String relType = apiLog.relType();
            createApiLogRequest.setRelType("".equals(relType) ? null : relType);
            createApiLogRequest.setRelId(resolveSpEl(joinPoint, apiLog.relId()));
            //主机
            String host;
            StringBuffer requestURL = request.getRequestURL();
            String queryString = request.getQueryString();
            if (queryString == null) {
                host = requestURL.toString();
            } else {
                host = requestURL.append('?').append(queryString).toString();
            }
            createApiLogRequest.setHost(host);
            //请求头
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.put(headerName, request.getHeader(headerName));
            }
            createApiLogRequest.setHeader(JSONUtil.toJsonStr(headers));
            //请求方式
            createApiLogRequest.setMethod(request.getMethod());
            //设置请求体
            createApiLogRequest.setBody(params);
            //设置创建时间
            createApiLogRequest.setCreatedAt(LocalDateTime.now());
            Object response = new Object();
            try {
                response = joinPoint.proceed();
            } catch (Exception e) {
                createApiLogRequest.setResponse(e.getMessage());
            }
            //设置更新时间
            createApiLogRequest.setUpdatedAt(LocalDateTime.now());
            //设置请求体
            createApiLogRequest.setResponse(JSONUtil.toJsonStr(response));
            apiLogService.createApiLog(createApiLogRequest);
            return response;
        } else {
            return joinPoint.proceed();
        }
    }


    //解析SpEl参数
    private String resolveSpEl(JoinPoint joinPoint, String expression) {
        if (!StringUtils.hasText(expression)) {
            return null;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();
        if (paramNames != null) {
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
        }
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);
        Object value = exp.getValue(context);
        return value != null ? value.toString() : null;
    }

}

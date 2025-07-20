package com.julan.tools.aop.apiLog.apiLogOut;

import cn.hutool.json.JSONUtil;
import com.julan.tools.aop.apiLog.context.ApiLogContext;
import com.julan.tools.aop.apiLog.context.ApiLogContextHolder;
import com.julan.tools.aop.apiLog.request.CreateApiLogRequest;
import com.julan.tools.service.ApiLogService;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @Project : tools
 * @Package : com.julan.tools.aop
 * @ClassName : ApiLogOutAspect
 * @Description : ApiLogOutAspect
 * @Author :  Hui Lee
 * @CreateTime : 2025/7/18 17:56
 * @Version : V1.0.0
 */
@Aspect
@Component
public class ApiLogOutAspect {

    @Resource
    private ApiLogService apiLogService;

    @Around("@annotation(apiLogOut)")
    public Object log(ProceedingJoinPoint joinPoint, ApiLogOut apiLogOut) throws Throwable {
        if (apiLogOut.enabled()) {
            ApiLogContext context = ApiLogContextHolder.get();
            CreateApiLogRequest log = new CreateApiLogRequest();
            if (context != null) {
                log.setName(context.getName());
                log.setTask(context.getTask());
                log.setCategory(context.getCategory());
                log.setRelType(context.getRelType());
                log.setRelId(context.getRelId());
            }
            log.setCreatedAt(LocalDateTime.now());

            Object[] args = joinPoint.getArgs();
            if (args.length > 0) log.setHost(args[0].toString());              // url
            if (args.length > 1) log.setHeader(JSONUtil.toJsonStr(args[1]));  // header
            if (args.length > 2) log.setBody(JSONUtil.toJsonStr(args[2]));    // body
            try {
                Object result = joinPoint.proceed();
                log.setResponse(JSONUtil.toJsonStr(result));
            } catch (Throwable e) {
                log.setResponse(JSONUtil.toJsonStr(new HashMap<>() {{
                    put("error", e.getMessage());
                }}));
            } finally {
                log.setUpdatedAt(LocalDateTime.now());
                apiLogService.createApiLog(log);
                ApiLogContextHolder.clear();
            }
        }
        return joinPoint.proceed();
    }
}

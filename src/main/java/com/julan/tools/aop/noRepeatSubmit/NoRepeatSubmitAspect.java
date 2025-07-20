package com.julan.tools.aop.noRepeatSubmit;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Project : tools
 * @Package : com.julan.tools.aop.noRepeatSubmit
 * @ClassName : NoRepeatSubmitAspect
 * @Description : NoRepeatSubmitAspect
 * @Author : Hui Lee
 * @CreateTime : 2025/7/20 10:40
 * @Version : V1.0.0
 */
@Aspect
@Component
@Slf4j
public class NoRepeatSubmitAspect {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(noRepeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String uri = request.getRequestURI();
        String ip = getClientIp(request);

        // 获取请求参数摘要（对 body + query 参数都进行摘要）
        String paramsDigest = getParamsDigest(request, joinPoint);

        // 生成 Redis Key
        String key = String.format("nrb:%s:%s:%s", uri, ip, paramsDigest);
        int expire = noRepeatSubmit.expire();

        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofSeconds(expire));
        if (Boolean.FALSE.equals(success)) {
            throw new RuntimeException("请勿重复提交！");
        }

        return joinPoint.proceed();
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private String getParamsDigest(HttpServletRequest request, ProceedingJoinPoint joinPoint) {
        Map<String, Object> paramMap = new HashMap<>();
        // 提取 GET 参数
        request.getParameterMap().forEach((k, v) -> paramMap.put(k, Arrays.toString(v)));
        // 提取方法参数（POST JSON）
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                continue;
            }
            paramMap.put("body", arg);
        }
        return SecureUtil.md5(JSONUtil.toJsonStr(paramMap));
    }
}
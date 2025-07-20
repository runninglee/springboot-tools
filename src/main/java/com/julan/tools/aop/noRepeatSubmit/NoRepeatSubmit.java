package com.julan.tools.aop.noRepeatSubmit;

import java.lang.annotation.*;

/**
 * @Project : tools
 * @Package : com.julan.tools.aop.noRepeatSubmit
 * @ClassName : NoRepeatSubmit
 * @Description : NoRepeatSubmit
 * @Author : Hui Lee
 * @CreateTime : 2025/7/20 10:39
 * @Version : V1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {
    /**
     * 过期时间，单位：秒
     */
    int expire() default 5;
}

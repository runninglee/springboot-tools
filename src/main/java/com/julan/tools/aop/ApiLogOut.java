package com.julan.tools.aop;

import java.lang.annotation.*;

/**
 * @Project : tools
 * @Package : com.julan.tools.aop
 * @ClassName : ApiLog
 * @Description : API LOG追踪
 * @Author : Hui Lee
 * @CreateTime : 2025/7/18 13:44
 * @Version : V1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLogOut {
    boolean enabled() default true; // 标识该方法是否启用日志
}

package com.julan.tools.aop;

import jakarta.validation.constraints.Null;

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
public @interface ApiLogIn {

    boolean enabled() default true; // 标识该方法是否启用日志

    String value() default "接口描述信息"; // 可用于描述接口

    String category() default "接收";

    String task() default "API";

    String relType() default "";

    String relId() default "";
}

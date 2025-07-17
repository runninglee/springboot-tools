package com.julan.tools.util.rule.isMobile;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule
 * @ClassName : isMobile
 * @Description : 手机号验证器
 * @Author : Hui Lee
 * @CreateTime : 2025/7/17 17:21
 * @Version : V1.0.0
 */
@Documented
@Constraint(validatedBy = IsMobileValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsMobile {
    String message() default "手机号格式不正确";

    boolean required() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

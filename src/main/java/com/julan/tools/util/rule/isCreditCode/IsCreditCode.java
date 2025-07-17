package com.julan.tools.util.rule.isCreditCode;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule.isCreditCode
 * @ClassName : IsCreditCode
 * @Description : 统一社会信用码验证器
 * @Author : Hui Lee
 * @CreateTime : 2025/7/17 17:39
 * @Version : V1.0.0
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = IsCreditCodeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsCreditCode {
    String message() default "统一社会信用代码格式不合法";

    boolean required() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

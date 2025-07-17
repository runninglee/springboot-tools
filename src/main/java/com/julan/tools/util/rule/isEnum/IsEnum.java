package com.julan.tools.util.rule.isEnum;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule.isEnum
 * @ClassName : IsEnum
 * @Description : 枚举值验证
 * @Author : HuiLee
 * @CreateTime : 2025/7/17 15:18
 * @Version : V1.0.0
 */
@Documented
@Constraint(validatedBy = IsEnumValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsEnum {

    String message() default "枚举值非法";

    String[] value();

    boolean required() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

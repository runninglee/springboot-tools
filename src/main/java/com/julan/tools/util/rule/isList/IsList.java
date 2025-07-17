package com.julan.tools.util.rule.isList;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule.IsList
 * @ClassName : IsList
 * @Description : 数组验证器
 * @Author : Hui Lee
 * @CreateTime : 2025/7/17 14:35
 * @Version : V1.0.0
 */

@Documented
@Constraint(validatedBy = IsListValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsList {

    String message() default "参数必须是列表";

    String[] value() default {};

    boolean required() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.julan.tools.util.rule.isNumeric;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = IsNumericValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsNumeric {

    String message() default "参数必须在0到150之间";

    boolean required() default true;       // 是否必填

    int min() default 0;                   // 最小值

    int max() default 150;                 // 最大值

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.julan.tools.util.rule.idCard;

import com.julan.tools.util.rule.isMobile.IsMobileValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule.idCard
 * @ClassName : IsIdCard
 * @Description : 身份证验证器
 * @Author : Hui Lee
 * @CreateTime : 2025/7/17 17:56
 * @Version : V1.0.0
 */
@Documented
@Constraint(validatedBy = IsIdCardValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsIdCard {
    String message() default "身份证格式不正确";

    boolean required() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

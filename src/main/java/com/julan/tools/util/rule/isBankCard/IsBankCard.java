package com.julan.tools.util.rule.isBankCard;

import com.julan.tools.util.rule.isList.IsListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule
 * @ClassName : isBankCard
 * @Description : 银行卡号验证器
 * @Author : Hui Lee
 * @CreateTime : 2025/7/17 17:08
 * @Version : V1.0.0
 */
@Documented
@Constraint(validatedBy = IsBankCardValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsBankCard {
    String message() default "银行卡号不合法";

    boolean required() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.julan.tools.util.rule.isUnique;

import com.julan.tools.util.rule.isBoolean.IsBooleanValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule.isUnique
 * @ClassName : IsUnique
 * @Description : 唯一性验证器
 * @Author : Hui Lee
 * @CreateTime : 2025/7/17 16:00
 * @Version : V1.0.0
 */
@Documented
@Constraint(validatedBy = IsUniqueValidation.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsUnique {
    String message() default "字段不唯一";

    String table();   // 表名

    String[] columns();  // 字段组合

    String id() default "id";   // ID 字段，用于更新时排除

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.julan.tools.util.rule.isEnum;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule.isEnum
 * @ClassName : IsEnum
 * @Description : 枚举值验证
 * @Author : huilee
 * @CreateTime : 2025/7/17 15:18
 * @Version : V1.0.0
 */

public class IsEnumValidation implements ConstraintValidator<IsEnum, String> {

    private Set<String> validValues;
    private boolean required;

    @Override
    public void initialize(IsEnum annotation) {
        this.validValues = new HashSet<>();
        this.required = annotation.required();
        validValues.addAll(Arrays.asList(annotation.value()));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 非必填且为空，合法
        if (!required && value == null) {
            return true;
        }
        return validValues.contains(value);
    }
}

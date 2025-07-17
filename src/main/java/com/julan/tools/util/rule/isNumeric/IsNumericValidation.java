package com.julan.tools.util.rule.isNumeric;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class IsNumericValidation implements ConstraintValidator<IsNumeric, Integer> {

    private boolean required;
    private int min;
    private int max;

    @Override
    public void initialize(IsNumeric annotation) {
        this.required = annotation.required();
        this.min = annotation.min();
        this.max = annotation.max();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 如果允许为空且为 null，合法
        if (!required && value == null) {
            return true;
        }
        // 如果是必填，但传了 null，非法
        if (required && value == null) {
            return false;
        }
        // 判断范围是否合法
        return value >= min && value <= max;
    }
}

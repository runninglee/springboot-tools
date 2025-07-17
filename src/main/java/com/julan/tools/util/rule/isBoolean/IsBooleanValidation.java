package com.julan.tools.util.rule.isBoolean;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsBooleanValidation implements ConstraintValidator<IsBoolean, Boolean> {
    private boolean required;

    @Override
    public void initialize(IsBoolean annotation) {
        this.required = annotation.required();
    }


    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        // 非必填且为空，合法
        if (!required && value == null) {
            return true;
        }
        // 必填但为 null，非法
        return !required || value != null;
    }
}

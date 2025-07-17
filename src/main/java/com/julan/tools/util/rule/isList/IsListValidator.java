package com.julan.tools.util.rule.isList;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule.IsList
 * @ClassName : IsListValidation
 * @Description : 数组验证器
 * @Author : huilee
 * @CreateTime : 2025/7/17 14:35
 * @Version : V1.0.0
 */
public class IsListValidator implements ConstraintValidator<IsList, List<?>> {

    private final Set<String> validValues = new HashSet<>();
    private boolean checkValue = false;
    private boolean required;

    @Override
    public void initialize(IsList annotation) {
        String[] values = annotation.value();
        if (values != null && values.length > 0) {
            checkValue = true;
            Collections.addAll(validValues, values);
        }
        this.required = annotation.required();
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        // 非必填且为空，合法
        if (!required && value == null) {
            return true;
        }

        // null 不合法
        if (required && value == null) {
            return false;
        }

        // 空数组合法，直接返回 true
        if (value.isEmpty()) {
            return true;
        }

        if (checkValue) {
            for (Object item : value) {
                if (item == null) {
                    return false;
                }
                if (!validValues.contains(item.toString())) {
                    return false;
                }
            }
        }

        return true;
    }
}

package com.julan.tools.util.rule.isMobile;

import com.julan.tools.util.rule.isBankCard.IsBankCard;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule
 * @ClassName : isMobile
 * @Description : 手机号验证器
 * @Author : Hui Lee
 * @CreateTime : 2025/7/17 17:21
 * @Version : V1.0.0
 */
public class IsMobileValidation implements ConstraintValidator<IsMobile, String> {
    private boolean required;

    private static final Pattern MOBILE_PATTERN = Pattern.compile(
            "^(\\+?86)?1[3-9]\\d{9}$" +                       // 大陆
            "|^(\\+?852)?[569]\\d{7}$" +                      // 香港
            "|^(\\+?853)?6\\d{7}$" +                          // 澳门
            "|^(\\+?886)?9\\d{8}$"                            // 台湾
    );

    @Override
    public void initialize(IsMobile annotation) {
        this.required = annotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果允许为空且为 null，合法
        if (!required && value == null) {
            return true;
        }
        // 如果是必填，但传了 null，非法
        if (required && value == null) {
            return false;
        }
        return MOBILE_PATTERN.matcher(value).matches();
    }
}

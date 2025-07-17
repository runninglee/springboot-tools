package com.julan.tools.util.rule.isBankCard;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule
 * @ClassName : isBankCard
 * @Description : 银行卡号验证器
 * @Author : Hui Lee
 * @CreateTime : 2025/7/17 17:08
 * @Version : V1.0.0
 */
public class IsBankCardValidation implements ConstraintValidator<IsBankCard, String> {

    private boolean required;

    private static final Pattern ID_BANK_CARD_PATTERN = Pattern.compile(
            "^[1-9]\\d{12,18}$"                     // 大陆，支持拓展新类型
    );

    @Override
    public void initialize(IsBankCard annotation) {
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
        //用 Luhm 校验算法
        //由卡号最后一位校验位（check digit）决定，能判断数字组合是否合法。
        if (!ID_BANK_CARD_PATTERN.matcher(value).matches()) return false;
        int sum = 0;
        boolean shouldDouble = false;
        for (int i = value.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(value.charAt(i));
            if (shouldDouble) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            shouldDouble = !shouldDouble;
        }
        return sum % 10 == 0;
    }
}

package com.julan.tools.util.rule.isCreditCode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule.isCreditCode
 * @ClassName : IsCreditCodeValidator
 * @Description : 统一社会信用码验证器
 * @Author : Hui Lee
 * @CreateTime : 2025/7/17 17:40
 * @Version : V1.0.0
 */
public class IsCreditCodeValidator implements ConstraintValidator<IsCreditCode, String> {

    private boolean required;

    private static final String BASE_CODE = "0123456789ABCDEFGHJKLMNPQRTUWXY";
    private static final int[] WEIGHT = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};

    private static final Pattern CREDIT_CODE_PATTERN = Pattern.compile("^[0-9A-Z]{18}$"                     // 大陆，支持拓展新类型
    );

    @Override
    public void initialize(IsCreditCode annotation) {
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

        // 前17位 通过加权因子 + 模11-2算法计算出 第18位校验码
        if (!CREDIT_CODE_PATTERN.matcher(value).matches()) {
            return false;
        }

        String code17 = value.substring(0, 17);
        char checkCode = value.charAt(17);

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            int codeIndex = BASE_CODE.indexOf(code17.charAt(i));
            if (codeIndex == -1) return false;
            sum += codeIndex * WEIGHT[i];
        }

        int logicCheckCodeIndex = (31 - (sum % 31)) % 31;
        char expected = BASE_CODE.charAt(logicCheckCodeIndex);
        return expected == checkCode;
    }
}

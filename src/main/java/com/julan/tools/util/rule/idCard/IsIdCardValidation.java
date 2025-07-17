package com.julan.tools.util.rule.idCard;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.rule.idCard
 * @ClassName : IsIdCardValidation
 * @Description : 身份证验证器
 * @Author :  Hui Lee
 * @CreateTime : 2025/7/17 17:56
 * @Version : V1.0.0
 */
@Slf4j
public class IsIdCardValidation implements ConstraintValidator<IsIdCard, String> {

    private boolean required;

    private static final Pattern ID_CARD = Pattern.compile("(^\\d{15}$)|(^\\d{17}([0-9X])$)");
    private static final int[] WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final char[] VALIDATE_CODES = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    @Override
    public void initialize(IsIdCard annotation) {
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
        if (value.length() != 15 && value.length() != 18) {
            return false;
        }

        value = value.toUpperCase();
        log.info(value);

        // 格式校验
        if (!ID_CARD.matcher(value).matches()) {
            return false;
        }

        // 15位转18位
        if (value.length() == 15) {
            value = convert15To18(value);
        }

        // 校验生日合法性
        String birth = value.substring(6, 14);
        if (!isValidDate(birth)) {
            return false;
        }

        // 校验码验证
        return checkVerifyCode(value);
    }

    private static boolean checkVerifyCode(String idCard) {
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            int digit = Character.getNumericValue(idCard.charAt(i));
            sum += digit * WEIGHT[i];
        }
        int mod = sum % 11;
        char code = VALIDATE_CODES[mod];

        log.info("{},{}", code, idCard);

        return code == idCard.charAt(17);
    }

    private static String convert15To18(String idCard) {
        StringBuilder sb = new StringBuilder(idCard.substring(0, 6));
        sb.append("19"); // 1900年代
        sb.append(idCard.substring(6));
        // 计算校验码
        String newId = sb.toString();
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            int digit = Character.getNumericValue(newId.charAt(i));
            sum += digit * WEIGHT[i];
        }
        int mod = sum % 11;
        sb.append(VALIDATE_CODES[mod]);
        return sb.toString();
    }

    private static boolean isValidDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

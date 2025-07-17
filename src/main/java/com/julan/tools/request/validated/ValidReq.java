package com.julan.tools.request.validated;

import com.julan.tools.util.rule.idCard.IsIdCard;
import com.julan.tools.util.rule.isBankCard.IsBankCard;
import com.julan.tools.util.rule.isBoolean.IsBoolean;
import com.julan.tools.util.rule.isCreditCode.IsCreditCode;
import com.julan.tools.util.rule.isEnum.IsEnum;
import com.julan.tools.util.rule.isList.IsList;
import com.julan.tools.util.rule.isMobile.IsMobile;
import com.julan.tools.util.rule.isNumeric.IsNumeric;
import com.julan.tools.util.rule.isUnique.IsUnique;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class ValidReq {

    /**
     * 判断整数
     * 类型: 自定义
     * 1、required 是否要做必填项
     * 2、min约束最小整数
     * 3、max约束最大整数
     * ======================================
     * 可验证: 数字、字符串数字
     * ======================================
     */
    @IsNumeric(message = "年龄必须在0到150之间")
    private Integer age; //必须是数字

    /**
     * 判断字符
     * 类型: 系统
     * 1、不能为空
     * 2、min 最小长度
     * 3、max 最大长度
     * ======================================
     * 可验证: 字符串、长度
     * ======================================
     */
    @NotBlank(message = "昵称必须不能为空")
    @Length(message = "昵称最大长度不能超过30", max = 30)
    private String name; //必须是字符串

    /**
     * 判断布尔
     * 类型: 自定义
     * 1、required 是否要做必填项
     * 2、布尔型验证 true/false/1/0
     * ======================================
     * 可验证: true、false、1、0
     * ======================================
     */
    @IsBoolean(message = "性别必须是布尔型")
    private Boolean sex; //必须是布尔

    /**
     * 判断数组
     * 类型: 自定义
     * 1、字符串、数字验证
     * 2、Array In
     * ======================================
     * 可验证: 字符串、数字
     * ======================================
     */
    @IsList(message = "编程语言必须是PHP、JAVA、GO、C++、C、PYTHON、JAVASCRIPT等", value = {"PHP", "JAVA", "GO", "C++", "C", "PYTHON", "JAVASCRIPT"})
    private List<String> languages;//必须是数组


    /**
     * 判断数组
     * 类型: 自定义
     * 1、字符串、数字验证
     * 2、Array In
     * ======================================
     * 可验证: 字符串、数字
     * ======================================
     */
    @IsList(message = "编程语言必须是100、200、300、400、500、600、700等", value = {"100", "200", "300", "400", "500", "600", "700"})
    private List<Integer> language;//必须是数组

    /**
     * 判断枚举
     * 类型: 自定义
     * 1、字符串、数字验证
     * ======================================
     * 可验证: 字符串、数字
     * ======================================
     */
    @IsEnum(message = "枚举的错误码必须是1001、1002、1003", value = {"1001", "1002", "1003"})
    private String code;//必须是数组

    /**
     * 判断是否唯一
     * 类型: 自定义
     * 1、查询指定表
     * 2、联合查询多个字段
     * 3、排除更新字段主键
     * ======================================
     * 可验证: 单表多字段,验证类需要单独构建
     * ======================================
     */
    @Valid
    @IsUnique(message = "手机号已经存在", table = "users", columns = {"mobile"})
    private UserDto user;


    /**
     * 判断手机号
     * 类型: 系统
     * 1、手机号不能为空
     * 2、正则手机号
     * ======================================
     * 可验证: 中国手机号
     * ======================================
     */
    @IsMobile
    private String mobile;

    /**
     * 判断身份证
     * 类型: 自定义
     * 1、身份证不能为空
     * 2、正则身份证
     * ======================================
     * 可验证: 大陆身份证号
     * ======================================
     */
    @IsIdCard(message = "身份证号格式不正确")
    private String idCard;

    /**
     * 判断营业执照
     * 类型: 自定义
     * 1、营业执照不能为空
     * 2、正则营业执照
     * ======================================
     * 可验证: 大陆营业执照
     * ======================================
     */
    @IsCreditCode(message = "营业执照格式不正确")
    private String creditCode;

    /**
     * 判断银行卡号
     * 类型: 自定义
     * 1、银行卡号不能为空
     * 2、正则银行卡号
     * ======================================
     * 可验证: 大陆银行卡号
     * ======================================
     */
    @IsBankCard(message = "银行卡号格式不正确", required = false)
    private String bankNo;


}

@Data
class UserDto {

    private Integer id;

    @NotBlank(message = "手机号不能为空")
    private String mobile;
}

package com.julan.tools.request.validated;

import com.julan.tools.util.rule.isBoolean.IsBoolean;
import com.julan.tools.util.rule.isEnum.IsEnum;
import com.julan.tools.util.rule.isList.IsList;
import com.julan.tools.util.rule.isNumeric.IsNumeric;
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

}

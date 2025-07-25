package com.julan.tools.request.validated;

import com.julan.tools.util.rule.idCard.IsIdCard;
import com.julan.tools.util.rule.isUnique.IsUnique;
import lombok.Data;

/**
 * @Description :
 * @Author : Hui Lee
 * @CreateTime : 2025/7/24 17:55
 * @Version : V1.0.0
 */
@Data
@IsUnique(message = "身份证号已经存在", table = "users", columns = {"id_card"})
public class ValidUniqReq {

    private Long id;

    @IsIdCard
    private String idCard;
}

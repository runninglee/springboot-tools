package com.julan.tools.request.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Project : tools
 * @Package : com.julan.tools.request.context
 * @ClassName : ApiLogContext
 * @Description : ApiLogContext
 * @Author : huilee
 * @CreateTime : 2025/7/18 18:18
 * @Version : V1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiLogContext {
    private String name;     // 接口名称
    private String task;     // 模块
    private String category; // 发起/回调
    private String relType;  // 业务类型（可选）
    private String relId;    // 业务 ID（可选）
}

package com.julan.tools.request.apiLog;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @Project : tools
 * @Package : com.julan.tools.request.apiLog
 * @ClassName : CreateApiLogRequest
 * @Description : 创建 Api Log 请求参数
 * @Author : Hui Lee
 * @CreateTime : 2025/7/18 15:29
 * @Version : V1.0.0
 */
@Data
public class CreateApiLogRequest {
    String category = "发起";
    String task;
    String name;
    String relType = null;
    String relId = null;
    String host;
    Object header;
    String method = "POST";
    Object body;
    Object response;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

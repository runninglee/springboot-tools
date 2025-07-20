package com.julan.tools.aop.apiLog.request;

import lombok.Data;

import java.time.LocalDateTime;

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

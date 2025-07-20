package com.julan.tools.service;

import com.julan.tools.entity.ApiLogEntity;
import com.julan.tools.repository.apiLog.ApiLogRepository;
import com.julan.tools.request.apiLog.CreateApiLogRequest;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Project : tools
 * @Package : com.julan.tools.service
 * @ClassName : ApiLogService
 * @Description : Api Log Service
 * @Author : Hui Lee
 * @CreateTime : 2025/7/18 15:27
 * @Version : V1.0.0
 */
@Component
public class ApiLogService {

    @Resource
    private ApiLogRepository apiLogRepository;

    public void createApiLog(CreateApiLogRequest request) {
        ApiLogEntity apiLog = new ApiLogEntity();
        BeanUtils.copyProperties(request, apiLog);
        apiLog.setCreatedAt(LocalDateTime.now());
        apiLog.setUpdatedAt(LocalDateTime.now());
        apiLogRepository.save(apiLog);
    }


}

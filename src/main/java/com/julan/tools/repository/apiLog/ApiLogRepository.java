package com.julan.tools.repository.apiLog;

import com.julan.tools.entity.ApiLogEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Project : tools
 * @Package : com.julan.tools.repository.apiLog
 * @ClassName : ApiLogRepository
 * @Description : Api Log Repository
 * @Author : Hui Lee
 * @CreateTime : 2025/7/18 14:53
 * @Version : V1.0.0
 */
@Repository
public interface ApiLogRepository extends CrudRepository<ApiLogEntity, Long>, JpaSpecificationExecutor<ApiLogEntity> {
}

package com.julan.tools.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;

import com.julan.tools.aop.ApiLog;
import com.julan.tools.entity.ApiLogEntity;
import com.julan.tools.request.apiLog.CreateApiLogRequest;
import com.julan.tools.request.validated.ValidReq;
import com.julan.tools.service.ApiLogService;
import com.julan.tools.util.api.ResultJson;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping("tool")
@Slf4j
public class ToolController {

    @Resource
    private ApiLogService apiLogService;


    @PostMapping("valid")
    public ResultJson<ValidReq> valid(@Valid @RequestBody ValidReq validReq) {
        return ResultJson.success(validReq);
    }

    @GetMapping("hutool")
    @ApiLog(value = "测试 HuTool 工具", category = "接收", task = "HuTool测试", relId = "#validReq.user.id")
    public ResultJson<Object> hutool(@Valid @RequestBody ValidReq validReq) {
        return ResultJson.success(IdUtil.objectId());
    }


    @GetMapping("http")
    public ResultJson<Object> http() {
        SortedMap<Object, Object> sortedMap = new TreeMap<>() {{
            put("attributes", "a");
            put("b", "b");
            put("c", "c");
        }};
        String req = JSONUtil.toJsonStr(sortedMap);
        CreateApiLogRequest request = new CreateApiLogRequest();
        request.setCreatedAt(LocalDateTime.now());
        request.setTask("AI 接口");
        request.setName("获取预审报告");
        request.setHeader(JSONUtil.toJsonStr(new HashMap<>() {{
            put("Content-Type", "application/json");
        }}));
        request.setHost("http:127.0.0.1/ai_report_v2/check");
        request.setBody(req);
        try {
            HttpResponse response = HttpRequest.post("http://127.0.0.1:8191/tool/hutool").header("Content-Type", "application/json").body(req).execute();
            if (JSONUtil.isTypeJSON(response.body())) {
                request.setResponse(JSONUtil.toJsonStr(response.body()));
            } else {
                request.setResponse(JSONUtil.toJsonStr(new HashMap<>() {{
                    put("data", JSONUtil.toJsonStr(response.body()));
                }}));
            }
        } catch (Exception e) {
            String result;
            if (JSONUtil.isTypeJSON(e.getMessage())) {
                result = JSONUtil.toJsonStr(e.getMessage());
            } else {
                result = JSONUtil.toJsonStr(new HashMap<>() {{
                    put("message", e.getMessage());
                }});
            }
            request.setResponse(result);
        }
        request.setUpdatedAt(LocalDateTime.now());
        ApiLogEntity apiLog = apiLogService.createApiLog(request);
        apiLog.setHeader(JSONUtil.parse(apiLog.getHeader()));
        apiLog.setBody(JSONUtil.parse(apiLog.getBody()));
        apiLog.setResponse(JSONUtil.parse(apiLog.getResponse()));

        return ResultJson.success(apiLog);
    }

}

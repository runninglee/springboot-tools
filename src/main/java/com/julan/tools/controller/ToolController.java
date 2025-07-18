package com.julan.tools.controller;

import cn.hutool.core.util.IdUtil;
import com.julan.tools.request.validated.ValidReq;
import com.julan.tools.util.api.ResultJson;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tool")
public class ToolController {


    @PostMapping("valid")
    public ResultJson<ValidReq> valid(@Valid @RequestBody ValidReq validReq) {
        return ResultJson.success(validReq);
    }


    @GetMapping("hutool")
    public ResultJson<Object> hutool() {
        return ResultJson.success(IdUtil.objectId());
    }

}

package com.julan.tools.controller;


import com.julan.tools.request.validated.ValidReq;
import com.julan.tools.util.api.ResultJson;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tool")
public class ToolController {


    @PostMapping("valid")
    public ResultJson<ValidReq> valid(@Valid @RequestBody ValidReq validReq) {
        return ResultJson.success(validReq);
    }

}

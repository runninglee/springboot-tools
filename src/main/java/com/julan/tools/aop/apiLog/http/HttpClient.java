package com.julan.tools.aop.apiLog.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.julan.tools.aop.apiLog.apiLogOut.ApiLogOut;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Project : tools
 * @Package : com.julan.tools.util.http
 * @ClassName : HttpClient
 * @Description : HttpClient
 * @Author : Hui Lee
 * @CreateTime : 2025/7/18 17:53
 * @Version : V1.0.0
 */
@Component
public class HttpClient {

    @ApiLogOut
    public HttpResponse post(String url, Map<String, String> headers, String body) {
        return HttpRequest.post(url).headerMap(headers, true).body(body).execute();
    }
}

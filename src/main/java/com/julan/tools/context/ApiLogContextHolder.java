package com.julan.tools.context;

import com.julan.tools.request.context.ApiLogContext;

/**
 * @Project : tools
 * @Package : com.julan.tools.context
 * @ClassName : ApiLogContextHolder
 * @Description : ApiLogContextHolder
 * @Author : Hui Lee
 * @CreateTime : 2025/7/18 18:18
 * @Version : V1.0.0
 */
public class ApiLogContextHolder {
    private static final ThreadLocal<ApiLogContext> CONTEXT = new ThreadLocal<>();

    public static void set(ApiLogContext context) {
        CONTEXT.set(context);
    }

    public static ApiLogContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}

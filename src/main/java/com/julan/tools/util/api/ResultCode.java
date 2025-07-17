package com.julan.tools.util.api;

public enum ResultCode implements ErrorCode {
    E_200(200, "请求成功"),
    E_500(500, "请求失败"),
    E_422(422, "请求参数检验失败"),
    E_401(401, "您的Token已经过期或未登录"),
    E_403(403, "您无访问权限");

    private final long code;
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

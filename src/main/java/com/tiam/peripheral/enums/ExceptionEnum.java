package com.tiam.peripheral.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tiam
 * @date 2023/10/23 19:29
 * @description 异常枚举
 */
@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    NOT_LOGIN(1001, "用户未登录, 请先登录!"),

    TOKEN_EXPIRED(1002, "token已过期, 请重新登录!"),

    SUCCESS(200, "成功!"),

    BODY_NOT_MATCH(400, "请求的数据格式不符!"),

    SIGNATURE_NOT_MATCH(401, "请求的数字签名不匹配!"),

    NOT_FOUND(404, "未找到该资源!"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),

    SERVER_BUSY(503, "服务器正忙，请稍后再试!");

    private final Integer code;
    private final String message;
}

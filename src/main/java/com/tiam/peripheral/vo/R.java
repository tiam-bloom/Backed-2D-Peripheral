package com.tiam.peripheral.vo;

import lombok.Data;

/**
 * @author Tiam
 * @date 2023/10/22 22:16
 * @description
 */
@Data
public class R <T>{
    private int code;
    private String msg;
    private T data;

    public R() {
    }

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> ok() {
        return new R<T>(200, "success");
    }

    public static <T> R<T> ok(String msg) {
        return new R<T>(200, msg);
    }

    public static <T> R<T> ok(T data) {
        return new R<T>(200, data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R<T>(200, msg, data);
    }

    public static <T> R<T> error() {
        return new R<T>(500, "error");
    }

    public static <T> R<T> error(String msg) {
        return new R<T>(500, msg);
    }
}

package com.tiam.peripheral.exception;

import com.tiam.peripheral.enums.ExceptionEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Tiam
 * @date 2023/10/23 19:27
 * @description 业务异常
 */

@Getter
@Setter
public class BizException extends RuntimeException {
    private Integer code;
    private String msg;

    public BizException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMessage();
    }

    public BizException(String msg) {
        super(msg);
        this.code = 1000;
        this.msg = msg;
    }

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}

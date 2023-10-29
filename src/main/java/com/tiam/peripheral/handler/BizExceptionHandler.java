package com.tiam.peripheral.handler;

import com.tiam.peripheral.exception.BizException;
import com.tiam.peripheral.vo.R;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Tiam
 * @date 2023/10/23 19:24
 * @description 全局异常处理
 */
@RestControllerAdvice
@Log4j2
public class BizExceptionHandler {

    @ExceptionHandler(value = Exception.class)   // 捕获异常类型
    public R<?> exceptionHandler(Exception e) {
        log.debug("全局异常捕获,错误原因>>>" + e.getMessage());
        return R.error("系统异常, 请联系管理员!");
    }

    @ExceptionHandler(value = BizException.class)
    public R<?> bizExceptionHandler(BizException e) {
        log.debug("业务异常捕获,错误原因>>>" + e.getMessage());
        return R.error(e);
    }
}

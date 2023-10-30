package com.tiam.peripheral.handler;

import com.tiam.peripheral.exception.BizException;
import com.tiam.peripheral.vo.R;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
        log.debug("全局异常捕获,错误原因>>> {}", e.getMessage());
        return R.error("系统异常, 请联系管理员!");
    }

    @ExceptionHandler(value = BizException.class)
    public R<?> bizExceptionHandler(BizException e) {
        log.debug("业务异常捕获,错误原因>>>" + e.getMessage());
        return R.error(e);
    }

    @ExceptionHandler(value = JwtException.class)
    public R<?> jwtExceptionHandler(JwtException e) {
        log.debug("token异常捕获,错误原因>>>" + e.getMessage());
        // 判断具体异常
        if(e.getClass().equals(ExpiredJwtException.class)){
            return R.error("token过期, 请重新登录!");
        }
        return R.error("token异常, 请重新登录!");
    }
}

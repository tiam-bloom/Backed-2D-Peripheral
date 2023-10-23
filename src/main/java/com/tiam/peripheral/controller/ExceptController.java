package com.tiam.peripheral.controller;

import com.tiam.peripheral.vo.R;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tiam.peripheral.enums.ExceptionEnum.INTERNAL_SERVER_ERROR;
import static com.tiam.peripheral.enums.ExceptionEnum.NOT_LOGIN;

/**
 * @author Tiam
 * @date 2023/10/23 19:18
 * @description
 */

@RestController
public class ExceptController implements ErrorController {
    @RequestMapping("/unLogin")
    public R<?> unLogin() {
        return R.error(NOT_LOGIN);
    }


    @RequestMapping("/error")
    public R<?> error() {
        return R.error(INTERNAL_SERVER_ERROR);
    }
}

package com.tiam.peripheral.controller;

import com.tiam.peripheral.entity.R;
import com.tiam.peripheral.entity.User;
import com.tiam.peripheral.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Tiam
 * @date 2023/10/19 17:50
 * @description
 */
@RestController
@Log4j2
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<?> login(@RequestBody @Validated User user) {
        log.info(user);

        User one = userService.query().eq("username", user.getUsername()).one();
        if (one == null)
            return R.error("用户不存在");
        if (!one.getPassword().equals(user.getPassword()))
            return R.error("密码错误");
        Map<String, Object> loginResult = userService.login(user);
        return R.ok("登录成功", loginResult);
    }
}

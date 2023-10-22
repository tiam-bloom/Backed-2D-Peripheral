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

    @PostMapping("/register")
    public R<?> register(@RequestBody @Validated User user) {
        log.info(user);
        User one = userService.query().eq("username", user.getUsername()).one();
        if (one != null)
            return R.error("用户名已存在");
        userService.save(user);
        return R.ok("注册成功");
    }

    @PostMapping("/refreshToken")
    public R<?> refreshToken(@RequestBody Map<String, String> map) {
        // { refreshToken: data.refreshToken }

        // return
//        {
//            accessToken: "eyJhbGciOiJIUzUxMiJ9.newAdmin",
//                    refreshToken: "eyJhbGciOiJIUzUxMiJ9.newAdminRefresh",
//                // `expires`选择这种日期格式是为了方便调试，后端直接设置时间戳或许更方便（每次都应该递增）。如果后端返回的是时间戳格式，前端开发请来到这个目录`src/utils/auth.ts`，把第`38`行的代码换成expires = data.expires即可。
//                expires: "2023/10/30 23:59:59"
//        }
        return R.ok("刷新成功");
    }
}

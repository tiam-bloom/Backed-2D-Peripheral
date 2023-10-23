package com.tiam.peripheral.controller;

import com.tiam.peripheral.vo.R;
import com.tiam.peripheral.entity.User;
import com.tiam.peripheral.service.UserService;
import com.tiam.peripheral.utils.RedisUtil;
import com.tiam.peripheral.utils.TokenUtil;
import com.tiam.peripheral.vo.LoginToken;
import com.tiam.peripheral.vo.Token;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
    public R<?> login(@RequestBody @Validated User user, HttpSession session) {
        log.info(user);

        User one = userService.query().eq("username", user.getUsername()).one();
        if (one == null)
            return R.error("用户不存在");
        if (!one.getPassword().equals(user.getPassword()))
            return R.error("密码错误");
        LoginToken loginResult = userService.login(user);
        session.setAttribute("username", user.getUsername());
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
    public R<?> refreshToken(@RequestBody Map<String, String> map, HttpSession session) {
        // { refreshToken: data.refreshToken }
        String refreshToken = map.get("refreshToken");
        if (refreshToken == null) {
            return R.error("refreshToken不能为空");
        }
        // 从session中获取用户名
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return R.error("用户未登录");
        }
        String name = RedisUtil.get(refreshToken);
        if (!StringUtils.equals(name, username)) {
            return R.error("refreshToken不正确");
        }
        Token token = TokenUtil.genToken(username);
        return R.ok("刷新成功", token);
    }

    @GetMapping("/unLogin")
    public R<?> unLogin() {
        return R.error("用户未登录, 请先登录!");
    }
}

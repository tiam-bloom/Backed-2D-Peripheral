package com.tiam.peripheral.controller;

import com.tiam.peripheral.entity.Role;
import com.tiam.peripheral.enums.ExceptionEnum;
import com.tiam.peripheral.exception.BizException;
import com.tiam.peripheral.service.RoleService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    @Autowired
    private RoleService roleService;

    @PostMapping("/login")
    public R<?> login(@RequestBody @Validated User user) {
        log.info("login: {}", user);
        User one = userService.query().eq("username", user.getUsername()).one();
        if (one == null) {
            throw new BizException("用户不存在");
        }
        if (!StringUtils.equals(one.getPassword(), user.getPassword())) {
            throw new BizException("密码错误");
        }
        LoginToken loginToken = userService.login(user);
        return R.ok("登录成功", loginToken);
    }

    @PostMapping("/register")
    public R<?> register(@RequestBody @Validated User user) {
        log.info("register: {}", user);
        User one = userService.query().eq("username", user.getUsername()).one();
        if (one != null) {
            throw new BizException("用户已注册");
        }
        boolean save = userService.save(user);
        return R.isOK(save, "注册成功");
    }

    @PostMapping("/refreshToken")
    public R<Token> refreshToken(@RequestBody Map<String, String> map) {
        // { refreshToken: data.refreshToken }
        String refreshToken = map.get("refreshToken");
        if (Objects.isNull(refreshToken)) {
            throw new BizException("refreshToken不能为空");
        }
        String username = RedisUtil.get(refreshToken);
        if(Objects.isNull(username)){
            throw new BizException("refreshToken不合法");
        }
        Role role = roleService.lambdaQuery().eq(Role::getRoleName, username).one();
        Token token = TokenUtil.genToken(username, role.getRoleName());
        return R.ok("刷新成功", token);
    }

    @GetMapping("/logout")
    public R<?> logout() {
        // todo 移除refreshToken
        // RedisUtil.del(TokenUtil.KEY);
        return R.ok("登出成功");
    }

    @GetMapping("/user/list")
    public R<List<User>> list() {
        List<User> list = userService.list();
        return R.ok(list);
    }




}

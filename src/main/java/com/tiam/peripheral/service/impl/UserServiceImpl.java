package com.tiam.peripheral.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiam.peripheral.entity.User;
import com.tiam.peripheral.mapper.UserMapper;
import com.tiam.peripheral.service.UserService;
import com.tiam.peripheral.utils.RedisUtil;
import com.tiam.peripheral.utils.TokenUtil;
import com.tiam.peripheral.vo.LoginToken;
import com.tiam.peripheral.vo.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author Tiam
 * @date 2023/10/22 22:04
 * @description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public LoginToken login(User user) {
        String username = user.getUsername();
        // todo 获取用户角色
        Token token = TokenUtil.genToken(username);

        return new LoginToken(token)
                .setUsername(username)
                .setRoles(Collections.singletonList("admin"));
    }
}

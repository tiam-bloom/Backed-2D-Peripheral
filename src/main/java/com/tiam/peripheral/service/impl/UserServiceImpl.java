package com.tiam.peripheral.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiam.peripheral.entity.Role;
import com.tiam.peripheral.entity.User;
import com.tiam.peripheral.exception.BizException;
import com.tiam.peripheral.mapper.RoleMapper;
import com.tiam.peripheral.mapper.UserMapper;
import com.tiam.peripheral.service.RedisService;
import com.tiam.peripheral.service.UserService;
import com.tiam.peripheral.utils.TokenUtil;
import com.tiam.peripheral.vo.LoginToken;
import com.tiam.peripheral.vo.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Tiam
 * @date 2023/10/22 22:04
 * @description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public LoginToken login(User user) {
        String username = user.getUsername();
        // 获取用户角色名
        Role role = roleMapper.selectById(user.getRoleId());

        Token token = genToken(username, role.getRoleName());

        return new LoginToken(token)
                .setUsername(username)
                .setRole(role.getRoleName());
    }

    @Override
    public User findUserByName(String name) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, name));
    }

    @Override
    public Token genNewToken(String refreshToken) {
        // 从redis中获取用户信息
        LoginToken loginToken = JSON.parseObject(redisService.get(refreshToken).toString(), LoginToken.class);
        if(Objects.isNull(loginToken)) {
            throw new BizException("refreshToken过期, 请重新登录!");
        }
        // 删除旧的refreshToken
        redisService.del(refreshToken);
        // 生成新的refreshToken
        return genToken(loginToken.getUsername(), loginToken.getRole());
    }

    private Token genToken(String username, String role) {
        // 生成refreshToken
        String refreshToken = UUID.randomUUID().toString();
        // 保存用户信息到redis
        redisService.set(refreshToken, new LoginToken(username, role), TokenUtil.REFRESH_EXPIRE);
        return TokenUtil.genToken(username, role, refreshToken);
    }
}

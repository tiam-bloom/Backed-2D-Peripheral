package com.tiam.peripheral.service.impl;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiam.peripheral.entity.User;
import com.tiam.peripheral.mapper.UserMapper;
import com.tiam.peripheral.service.UserService;
import com.tiam.peripheral.utils.RedisUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Tiam
 * @date 2023/10/22 22:04
 * @description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public Map<String, Object> login(User user) {
        Map<String, Object> loginResult = new HashMap<>();
        loginResult.put("username", user.getUsername());
        loginResult.put("roles", new String[]{"admin"});
        String accessToken = UUID.randomUUID().toString();
        // todo 存入redis 设置过期时间
        loginResult.put("accessToken", accessToken);

        String refreshToken = UUID.randomUUID().toString();

        loginResult.put("refreshToken", refreshToken);
        loginResult.put("expires", Instant.now().plusMillis(1000 * 60 * 60 * 24 * 7).toEpochMilli());
        return loginResult;
    }
}

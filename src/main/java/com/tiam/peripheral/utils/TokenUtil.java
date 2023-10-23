package com.tiam.peripheral.utils;

import com.tiam.peripheral.vo.Token;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

/**
 * @author Tiam
 * @date 2023/10/23 16:38
 * @description
 */
public class TokenUtil {
    public static final int ACCESS_EXPIRE = 60;
    public static final int REFRESH_EXPIRE = 60 * 60 * 24 * 7;

    public static Token genToken(String username){
        String accessToken = UUID.randomUUID().toString();
        // 存入redis 设置过期时间
        RedisUtil.set(accessToken, username, ACCESS_EXPIRE);
        String refreshToken = UUID.randomUUID().toString();
        RedisUtil.set(refreshToken, username, REFRESH_EXPIRE);
        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expire(new Timestamp(Instant.now().plusSeconds(ACCESS_EXPIRE).toEpochMilli()))
                .build();
    }
}

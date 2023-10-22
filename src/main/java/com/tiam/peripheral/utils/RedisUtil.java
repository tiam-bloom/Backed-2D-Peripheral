package com.tiam.peripheral.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Tiam
 * @date 2023/10/22 23:03
 * @description
 */
@Log4j2
@Component
@SuppressWarnings("unused")
public class RedisUtil implements ApplicationContextAware {

    /**
     * 使用StringRedisTemplate(，其是RedisTemplate的定制化升级)
     */
    private static StringRedisTemplate redisTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisUtil.redisTemplate = applicationContext.getBean(StringRedisTemplate.class);
    }

    public static boolean set(String key, String value) {
        return Boolean.TRUE.equals(redisTemplate.execute((RedisConnection connection) -> {
            connection.set(key.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8));
            return true;
        }));
    }

    public static boolean set(String key, String value, long timeout) {
        return Boolean.TRUE.equals(redisTemplate.execute((RedisConnection connection) -> {
            connection.set(key.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8),
                    Expiration.from(timeout, TimeUnit.SECONDS), RedisStringCommands.SetOption.UPSERT);
            return true;
        }));
    }

    // get
    public static String get(String key) {
        return redisTemplate.execute((RedisConnection connection) -> {
            byte[] bytes = connection.get(key.getBytes(StandardCharsets.UTF_8));
            return bytes == null ? null : new String(bytes, StandardCharsets.UTF_8);
        });
    }


}

package com.tiam.peripheral;

import com.alibaba.fastjson2.JSON;
import com.tiam.peripheral.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tiam
 * @date 2023/11/2 23:01
 * @description
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void test(){
        String key = "test";
        Map<String, Object> map = new HashMap<>();
        map.put("username", "Tiam");
        map.put("role", "admin");
        redisService.set(key, JSON.toJSONString(map));
        redisService.set(key+"1", map);
    }

    @Test
    public void test1(){
        String key = "274656cc-97c3-4936-9ef4-8d3d00c2ab2d";
        System.out.println(redisService.del(key));
    }
}

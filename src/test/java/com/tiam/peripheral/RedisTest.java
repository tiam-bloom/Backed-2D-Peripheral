package com.tiam.peripheral;

import com.alibaba.fastjson2.JSON;
import com.tiam.peripheral.utils.RedisUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tiam
 * @date 2023/11/2 23:01
 * @description
 */
public class RedisTest {
    @Test
    public void test(){
        String key = "test";
        Map<String, Object> map = new HashMap<>();
        map.put("username", "Tiam");
        map.put("role", "admin");
        RedisUtil.set(key, JSON.toJSONString(map));
    }
}

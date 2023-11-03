package com.tiam.peripheral.service;

import java.util.List;

/**
 * @author Tiam
 * @date 2023/11/3 13:58
 * @description
 */
public interface RedisService {
    /**
     * 保存属性
     *
     * @param key   key值
     * @param value value值
     * @param time  时间戳
     */
    void set(String key, Object value, long time);

    /**
     * 保存属性
     *
     * @param key   key值
     * @param value value值
     */
    void set(String key, Object value);

    /**
     * 获取属性
     *
     * @param key key值
     * @return 返回对象
     */
    Object get(String key);

    /**
     * 删除属性
     *
     * @param key key值
     * @return 返回成功
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     *
     * @param keys key值集合
     * @return 返回删除数量
     */
    Long del(List<String> keys);

    /**
     * 设置过期时间
     *
     * @param key  key值
     * @param time 时间戳
     * @return 返回成功
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     *
     * @param key key值
     * @return 返回时间戳
     */
    Long getExpire(String key);

    /**
     * 判断key是否存在
     *
     * @param key key值
     * @return 返回
     */
    Boolean hasKey(String key);
}

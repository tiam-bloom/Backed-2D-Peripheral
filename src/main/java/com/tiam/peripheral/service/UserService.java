package com.tiam.peripheral.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiam.peripheral.entity.User;

import java.util.Map;

/**
 * @author Tiam
 * @date 2023/10/22 22:03
 * @description
 */
public interface UserService extends IService<User> {
    Map<String, Object> login(User user);
}

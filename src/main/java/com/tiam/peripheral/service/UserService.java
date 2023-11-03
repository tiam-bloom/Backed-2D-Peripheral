package com.tiam.peripheral.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiam.peripheral.entity.User;
import com.tiam.peripheral.vo.LoginToken;
import com.tiam.peripheral.vo.Token;

/**
 * @author Tiam
 * @date 2023/10/22 22:03
 * @description
 */
public interface UserService extends IService<User> {
    LoginToken login(User user);

    User findUserByName(String name);

    /**
     * 刷新token
     * @param refreshToken 旧的refreshToken
     * @return 新的token
     */
    Token genNewToken(String refreshToken);
}

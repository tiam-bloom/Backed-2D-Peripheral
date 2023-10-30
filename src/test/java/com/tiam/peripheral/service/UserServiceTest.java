package com.tiam.peripheral.service;

import com.tiam.peripheral.entity.User;
import com.tiam.peripheral.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

/**
 * @author Tiam
 * @date 2023/10/22 22:07
 * @description
 */
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testQuery() {
        System.out.println(userService.getById(1));
    }

    @Test
    public void testInsert() {
        System.out.println(userService.save(new User().setUsername("admin").setPassword("123456")));
    }

    @Autowired
    private UserMapper userMapper;
    @Test
    public void test(){
        User user = userMapper.findUserByUsername("xixi");
        System.out.println(user);
        System.out.println(Objects.nonNull(user));
    }
}

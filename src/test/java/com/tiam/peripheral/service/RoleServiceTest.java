package com.tiam.peripheral.service;

import com.tiam.peripheral.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Tiam
 * @date 2023/10/29 16:12
 * @description
 */
@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void test(){
        Role role = new Role();
        role.setRoleName("common");
        role.setRoleLabel("普通用户");
        System.out.println(roleService.save(role));
    }


}

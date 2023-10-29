package com.tiam.peripheral.controller;

import com.tiam.peripheral.entity.Role;
import com.tiam.peripheral.service.RoleService;
import com.tiam.peripheral.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Tiam
 * @date 2023/10/29 16:11
 * @description
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public R<?> list() {
        List<Role> list = roleService.list();
        return R.ok("查询成功", list);
    }


}

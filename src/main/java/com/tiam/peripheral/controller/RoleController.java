package com.tiam.peripheral.controller;

import com.tiam.peripheral.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tiam
 * @date 2023/10/29 16:11
 * @description
 */
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;


}

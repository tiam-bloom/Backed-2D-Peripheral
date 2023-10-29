package com.tiam.peripheral.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiam.peripheral.entity.Role;
import com.tiam.peripheral.mapper.RoleMapper;
import com.tiam.peripheral.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Tiam
 * @date 2023/10/29 16:09
 * @description
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


}

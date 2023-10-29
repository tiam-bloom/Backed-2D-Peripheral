package com.tiam.peripheral.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Tiam
 * @date 2023/10/29 16:02
 * @description
 */
@Data
@TableName("tb_role")
public class Role {


    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * role_name
     */
    private String roleName;

    /**
     * role_label
     */
    private String roleLabel;
}
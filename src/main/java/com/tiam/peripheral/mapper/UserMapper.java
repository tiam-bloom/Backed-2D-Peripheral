package com.tiam.peripheral.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiam.peripheral.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Tiam
 * @date 2023/10/22 22:02
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from tb_user where username = #{username}")
    User findUserByUsername(String username);
}

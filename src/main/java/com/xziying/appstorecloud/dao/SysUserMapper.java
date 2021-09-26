package com.xziying.appstorecloud.dao;

import com.xziying.appstorecloud.domain.sys.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UserMapper
 *
 * @author : xziying
 * @create : 2021-03-24 17:34
 */
@Component
public interface SysUserMapper {

    @Insert("insert into `sys_user`(account,password,email,phone,time) values(#{account},#{password},#{email},#{phone},#{time})")
    int add(User user);

    @Select("SELECT * FROM `sys_user`")
    List<User> queryAll();

    @Select("SELECT * FROM `sys_user` WHERE account = #{param1}")
    User queryByAccount(String account);

    @Select("SELECT * FROM `sys_user` WHERE email = #{param1}")
    User queryByEmail(String email);

    @Select("SELECT * FROM `sys_user` WHERE phone = #{param1}")
    User queryByPhone(String phone);
}

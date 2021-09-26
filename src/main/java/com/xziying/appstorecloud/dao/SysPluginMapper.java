package com.xziying.appstorecloud.dao;

import com.xziying.appstorecloud.domain.sys.Plugin;
import com.xziying.appstorecloud.domain.sys.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SysPluhinMapper
 *
 * @author : xziying
 * @create : 2021-03-31 15:36
 */
@Component
public interface SysPluginMapper {

    @Select("SELECT * FROM `sys_plugin`")
    List<Plugin> queryAll();

    @Select("SELECT * FROM `sys_plugin` WHERE clazz=#{param1}")
    int getPicByClazz(String clazz);


}

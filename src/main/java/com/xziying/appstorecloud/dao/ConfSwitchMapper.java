package com.xziying.appstorecloud.dao;

import com.xziying.appstorecloud.domain.conf.ConfSwitch;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ConfSwitchMapper
 *
 * @author : xziying
 * @create : 2021-04-09 20:36
 */
@Component
public interface ConfSwitchMapper {

    @Insert("insert into `conf_switch`(`uid`,`clazz`,`weight`,`state`) values(#{uid},#{clazz},#{weight},#{state})")
    int add(ConfSwitch confSwitch);

    @Update("UPDATE `conf_switch` " +
            "SET `weight`=#{weight},`state`=#{state} " +
            "WHERE `uid`=#{uid} AND `clazz`=#{clazz}")
    int update(ConfSwitch confSwitch);

    @Update("UPDATE `conf_switch` " +
            "SET `weight`=#{param3} " +
            "WHERE `uid`=#{param1} AND `clazz`=#{param2}")
    int updateWeight(int uid, String clazz, int weight);

    @Select("SELECT * FROM `conf_switch` WHERE uid=#{param1}")
    List<ConfSwitch> queryListByUid(int uid);

}

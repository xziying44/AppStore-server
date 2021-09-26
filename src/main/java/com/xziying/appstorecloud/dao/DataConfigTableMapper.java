package com.xziying.appstorecloud.dao;

import com.xziying.appstorecloud.domain.data.ConfigTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ConfigTableMapper
 *
 * @author : xziying
 * @create : 2021-04-14 15:22
 */
@Component
public interface DataConfigTableMapper {

    @Insert("insert into `data_configtable`(`uid`,`clazz`,`fromQQ`,`num`) values(#{uid},#{clazz},#{fromQQ},#{num})")
    int add(ConfigTable configTable);

    @Update("UPDATE `data_configtable` " +
            "SET `num`= `num` + #{param2} " +
            "WHERE `dcid`=#{param1}")
    int counter(int dcid, int num);

    @Select("SELECT * FROM `data_configtable` WHERE uid=#{param1} AND clazz=#{param2}")
    List<ConfigTable> queryByClazz(int uid, String clazz);
}

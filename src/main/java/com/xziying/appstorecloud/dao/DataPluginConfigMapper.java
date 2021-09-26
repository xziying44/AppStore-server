package com.xziying.appstorecloud.dao;

import com.xziying.appstorecloud.domain.data.PluginConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DataPluginConfigMapper
 *
 * @author : xziying
 * @create : 2021-03-31 16:02
 */
@Component
public interface DataPluginConfigMapper {

    @Insert("insert into `data_pluginconfig`(`uid`,`pid`,`fromQQ`,`key`,`value`) values(#{uid},#{pid},#{fromQQ},#{key},#{value})")
    int add(PluginConfig pluginConfig);

    @Update("UPDATE `data_pluginconfig` " +
            "SET `key`=#{key},`value`=#{value}" +
            "WHERE `pcid`=#{pcid}")
    int update(PluginConfig pluginConfig);

    @Update("UPDATE `data_pluginconfig` " +
            "SET `value`=#{value}" +
            "WHERE `uid`=#{uid} AND `pid`=#{pid} AND `fromQQ`=#{fromQQ} AND `key`=#{key}")
    int updateByPid(PluginConfig pluginConfig);

    @Select("SELECT * FROM `data_pluginconfig` WHERE uid=#{param1} AND pid=#{param2} AND fromQQ=#{param3}")
    List<PluginConfig> queryConfig(int uid, int pid, String fromQQ);

}

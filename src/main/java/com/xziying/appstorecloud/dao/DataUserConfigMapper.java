package com.xziying.appstorecloud.dao;

import com.xziying.appstorecloud.domain.data.UserConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UserConfigMapper
 *
 * @author : xziying
 * @create : 2021-04-14 15:22
 */
@Component
public interface DataUserConfigMapper {

    @Insert("insert into `data_userconfig`(`dcid`,`key`,`value`,`noobj`) values(#{dcid},#{key},#{value},#{noobj})")
    int add(UserConfig userConfig);

    @Update("UPDATE `data_userconfig` " +
            "SET `dcid`=#{dcid} " +
            ", `key`=#{key} " +
            ", `value`=#{value} " +
            ", `noobj`=#{noobj} " +
            ", `activate`=1 " +
            "WHERE `duid`=#{duid}")
    int update(UserConfig userConfig);

    @Select("SELECT * FROM `data_userconfig` WHERE `dcid`=#{param1} AND `activate`=1")
    List<UserConfig> queryByDcid(int dcid);

    @Select("SELECT * FROM `data_userconfig` WHERE `duid`=#{param1} AND `activate`=1")
    UserConfig querySingle(int duid);

    @Select("SELECT * FROM `data_userconfig` WHERE `dcid`=#{param1} AND `key`=#{param2} AND `activate`=1")
    UserConfig queryByKey(int dcid, String key);

    @Update("UPDATE `data_userconfig` " +
            "SET `activate`=0 " +
            "WHERE `duid`=#{param1}")
    int blanking(int duid);

    @Select("SELECT `duid` FROM `data_userconfig` WHERE `activate`=0 LIMIT 1")
    int takeEmptyPosition();


}

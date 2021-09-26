package com.xziying.appstorecloud.dao;

import com.xziying.appstorecloud.domain.filter.Verification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * FilterVerification
 *
 * @author : xziying
 * @create : 2021-04-12 19:55
 */
@Component
public interface FilterVerificationMapper {

    @Insert("INSERT INTO `filter_verification` (`uid`, `clazz`, `fromQQ`, `start`, `end`) VALUES " +
            "(#{uid}, #{clazz}, #{fromQQ}, #{start}, #{end})")
    int add(Verification verification);

    @Update("UPDATE `filter_verification` " +
            "SET `end` = #{end} " +
            "WHERE `fvid` = #{fvid}")
    int update(Verification verification);

    @Select("SELECT * FROM `filter_verification` WHERE `uid`=#{param1}")
    List<Verification> queryByUid(int uid);

    @Select("SELECT * FROM `filter_verification` " +
            "WHERE `uid`=#{param1} AND `clazz`=#{param2} AND `fromQQ`=#{param3}")
    Verification querySingle(int uid, String clazz, String fromQQ);

}

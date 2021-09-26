package com.xziying.appstorecloud.dao;

import com.xziying.appstorecloud.domain.pay.Wallet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * PayWalletMapper
 *
 * @author : xziying
 * @create : 2021-04-28 15:51
 */
@Component
public interface PayWalletMapper {

    /**
     * 创建钱包
     * @param uid 用户id
     * @return  成功1
     */
    @Insert("INSERT INTO `pay_wallet` (`uid`) VALUES " +
            "(#{param1})")
    int create(int uid);

    /**
     * 更新数据
     */
    @Update("UPDATE `pay_wallet` " +
            "SET `uid`=#{uid} " +
            ", `balance`=#{balance} " +
            ", `integral`=#{integral} " +
            ", `consume`=#{consume} " +
            "WHERE `pwid`=#{pwid}")
    int update(Wallet wallet);

    @Update("UPDATE `pay_wallet` SET `balance` = `balance` + #{param2} WHERE `uid` = #{param1}")
    int balanceOperating(int uid, double val);


    @Select("SELECT * FROM `pay_wallet` WHERE `uid`=#{param1}")
    Wallet query(int uid);
}

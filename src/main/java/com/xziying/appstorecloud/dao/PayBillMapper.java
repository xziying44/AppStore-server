package com.xziying.appstorecloud.dao;

import com.xziying.appstorecloud.domain.pay.Bill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * PayBillMapper
 *
 * @author : xziying
 * @create : 2021-05-02 20:58
 */
public interface PayBillMapper {
    /**
     * 创建充值订单
     */
    @Insert("INSERT INTO `pay_bill` (`uid`, `orderid`, `type`, `ordername`, `amount`, `time`, `status`) VALUES " +
            "(#{uid}, #{orderid}, #{type}, #{ordername}, #{amount}, #{time}, #{status})")
    int create(Bill bill);

    @Select("SELECT * FROM `pay_bill` WHERE `uid`=#{param1}")
    List<Bill> queryByUid(int uid);

    @Select("SELECT * FROM `pay_bill` WHERE `orderid`=#{param1}")
    Bill queryByOrderid(String orderid);


    @Update("UPDATE `pay_bill` SET `status` = #{param2} WHERE `orderid` = #{param1}")
    int modifyStatus(String orderid, int status);

    @Select("SELECT * FROM `pay_bill` WHERE `uid`=#{param1} AND `status`=0")
    List<Bill> queryToBePaidByUid(int uid);
}

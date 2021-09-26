package com.xziying.appstorecloud.domain.pay;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Bill 账单
 *
 * @author : xziying
 * @create : 2021-05-02 17:05
 */
@Data
@Accessors(chain = true)
public class Bill implements Serializable {
    Integer pbid;   // id
    Integer uid;    // 用户id
    String orderid; // 订单号
    Integer type;   // 类型 0支付宝 1微信
    String ordername;   // 订单名
    Double amount;  // 付款余额
    Long time;      // 创建订单时间戳
    Integer status; // 订单状态 0未支付 1已支付 2支付失败
}

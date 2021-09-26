package com.xziying.appstorecloud.domain.pay;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * WeChatPayBean
 *
 * @author : xziying
 * @create : 2021-05-02 16:02
 */
@Data
@Accessors(chain = true)
public class WeChatPayBean implements Serializable {
    /**
     * 订单号
     */
    private String orderId;

    /**
     * 付款余额
     */
    private Double orderAmount;

    /**
     * 订单名称
     */
    private String orderName;
}

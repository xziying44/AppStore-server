package com.xziying.wechatpay.service;

/**
 * WeChatPayService
 *
 * @author : xziying
 * @create : 2021-05-03 11:13
 */
public interface WeChatPayService {

    /**
     * 发起支付
     * @param orderId 订单id
     * @param orderAmount   支付余额
     * @param orderName 订单名
     */
    String nativePay(String orderId, Double orderAmount, String orderName);

    /**
     * 发起支付
     * @param orderId 订单id
     * @param orderAmount   支付余额
     * @param orderName 订单名
     */
    String nativePay_phone(String orderId, Double orderAmount, String orderName);

    /**
     * 查询订单
     * @param orderId 订单id
     */
    String query(String orderId);
}

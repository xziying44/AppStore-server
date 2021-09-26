package com.xziying.appstorecloud.constant;

/**
 * KeyField
 *
 * @author : xziying
 * @create : 2021-03-28 21:40
 */
public interface KeyField {
    String SIGNATURE = "xziying@vip.qq.com";

    /**
     * 支付宝支付
     */
    int PAY_ALIPAY = 0;

    /**
     * 微信支付
     */
    int PAY_WECHAT = 1;

    /**
     * 等待支付
     */
    int PAY_STATUS_WAIT = 0;

    /**
     * 支付成功
     */
    int PAY_STATUS_SUCCESS = 1;

    /**
     * 支付失败
     */
    int PAY_STATUS_FAILURE = 2;
}

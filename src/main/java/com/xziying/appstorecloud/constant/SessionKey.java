package com.xziying.appstorecloud.constant;

/**
 * SessionKey
 *
 * @author : xziying
 * @create : 2021-04-28 17:38
 */
public interface SessionKey {
    /**
     * 验证码
     */
    String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    /**
     * 用户信息
     */
    String USER_INFO = "USER_INFO";

    /**
     * Redis手机号验证码标记头
     */
    String REDIS_PHONE_CODE = "REDIS_PHONE_CODE";

    /**
     * 手机号码
     */
    String SESSION_PHONE_NUMBER = "SESSION_PHONE_NUMBER";

    /**
     * 手机验证码
     */
    String SESSION_PHONE_CODE = "SESSION_PHONE_CODE";

    /**
     * 手机验证码时间
     */
    String SESSION_PHONE_CODE_TIME = "SESSION_PHONE_CODE_TIME";

    /**
     * 支付订单
     */
    String SESSION_PAY_BILL = "SESSION_PAY_BILL";

}

package com.xziying.appstorecloud.service;

import com.xziying.appstorecloud.domain.sys.User;

/**
 * WebUserService
 *
 * @author : xziying
 * @create : 2021-04-28 16:02
 */
public interface WebUserService {

    /**
     * 登录
     */
    User login(String account, String password);

    /**
     * 检测手机号是否被注册
     * @param phone 手机号
     * @return 被注册返回真 没注册返回假
     */
    boolean doesThePhoneExist(String phone);

    /**
     * 检测用户名是否被注册
     * @param account 用户名
     * @return 被注册返回真 没注册返回假
     */
    boolean doesTheAccountExist(String account);

    /**
     * 注册 成功返回0 失败返回其他
     */
    int register(String phone, String account, String password);


}

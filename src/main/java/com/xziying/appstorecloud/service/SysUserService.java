package com.xziying.appstorecloud.service;

import com.xziying.appstorecloud.domain.sys.User;

/**
 * SysUserService
 *
 * @author : xziying
 * @create : 2021-03-28 20:08
 */
public interface SysUserService {

    /**
     * 登录
     * @param account 账号
     * @param password  密码
     * @return  成功返回用户对象，
     * 失败抛出
     * AccountDoesNotExistException(账号不存在)
     * IncorrectAccountPasswordException(密码错误)
     * 的异常
     */
    User login(String account, String password);

    /**
     * 获取登录令牌
     * @param user 用户对象
     * @return 成功返回token令牌
     */
    String getToken(User user);
}

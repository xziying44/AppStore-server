package com.xziying.appstorecloud.abnormal;

/**
 * IncorrectAccountPasswordException
 *
 * @author : xziying
 * @create : 2021-03-28 20:13
 */
public class IncorrectAccountPasswordException extends RuntimeException{
    public IncorrectAccountPasswordException() {
        super("账号密码不正确");
    }
}

package com.xziying.appstorecloud.abnormal;

/**
 * AccountDoesNotExistException
 *  账号不存在
 * @author : xziying
 * @create : 2021-03-28 20:04
 */
public class AccountDoesNotExistException extends RuntimeException{

    public AccountDoesNotExistException() {
        super("账号不存在");
    }
}

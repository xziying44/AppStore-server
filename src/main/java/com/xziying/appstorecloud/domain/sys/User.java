package com.xziying.appstorecloud.domain.sys;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User
 *
 * @author : xziying
 * @create : 2021-03-24 17:10
 */
public class User implements Serializable {
    Integer uid;        // uid
    String account;     // 用户名
    String password;    // 密码
    String email;      // 邮箱
    String phone;      // 手机
    Long time;          // 注册时间戳

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", time=" + time +
                '}';
    }
}

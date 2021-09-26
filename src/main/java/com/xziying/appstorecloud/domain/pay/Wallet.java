package com.xziying.appstorecloud.domain.pay;

/**
 * Wallet 钱包实体
 *
 * @author : xziying
 * @create : 2021-04-28 14:59
 */
public class Wallet {
    Integer pwid;       // 序号
    Integer uid;        // 用户id
    Double balance;     // 余额
    Integer integral;   // 积分
    Double consume;     // 总消费

    public Wallet() {
    }

    public Integer getPwid() {
        return pwid;
    }

    public void setPwid(Integer pwid) {
        this.pwid = pwid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "pwid=" + pwid +
                ", uid=" + uid +
                ", balance=" + balance +
                ", integral=" + integral +
                ", consume=" + consume +
                '}';
    }
}

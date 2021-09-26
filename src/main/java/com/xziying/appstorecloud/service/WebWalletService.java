package com.xziying.appstorecloud.service;

import com.xziying.appstorecloud.domain.pay.Bill;
import com.xziying.appstorecloud.domain.pay.Wallet;

import java.util.List;

/**
 * WebWalletService 钱包相关服务
 *
 * @author : xziying
 * @create : 2021-05-02 20:00
 */
public interface WebWalletService {

    /**
     * 查询钱包数据
     */
    Wallet queryWallet(int uid);

    /**
     * 创建订单
     */
    Bill createBill(int uid, String account, double amount, int type);

    void askOrder(String orderId);

    /**
     * 完成订单充值余额
     */
    void completeBill(String orderId);

    List<Bill> queryByUid(int uid);


    /**
     * 轮询订单状态
     */
    void pollOrderStatus(int uid);

}

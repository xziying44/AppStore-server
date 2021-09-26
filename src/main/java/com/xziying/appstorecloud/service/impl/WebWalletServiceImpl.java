package com.xziying.appstorecloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xziying.appstorecloud.constant.KeyField;
import com.xziying.appstorecloud.dao.PayBillMapper;
import com.xziying.appstorecloud.dao.PayWalletMapper;
import com.xziying.appstorecloud.domain.pay.AlipayBean;
import com.xziying.appstorecloud.domain.pay.Bill;
import com.xziying.appstorecloud.domain.pay.Wallet;
import com.xziying.appstorecloud.service.WebWalletService;
import com.xziying.appstorecloud.utils.pay.AlipayUtil;
import com.xziying.wechatpay.service.WeChatPayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * WebWalletServiceImpl
 *
 * @author : xziying
 * @create : 2021-05-02 20:01
 */
@Service
public class WebWalletServiceImpl implements WebWalletService {

    @Resource
    PayWalletMapper walletMapper;

    @Resource
    PayBillMapper billMapper;

    @Resource
    AlipayUtil alipayUtil;

    @Resource
    WeChatPayService weChatPayService;

    @Override
    public Wallet queryWallet(int uid) {
        return walletMapper.query(uid);
    }

    @Override
    public Bill createBill(int uid, String account, double amount, int type) {
        long time = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder orderid = new StringBuilder().append(sdf.format(new Date(time)));

        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 3; i++) {
            orderid.append(random.nextInt(10));
        }

        String ordername = "余额充值" + amount + "元(用户: " +account + ")";

        Bill bill = new Bill()
                .setUid(uid)
                .setOrderid(orderid.toString())
                .setType(type)
                .setOrdername(ordername)
                .setAmount(amount)
                .setTime(time)
                .setStatus(KeyField.PAY_STATUS_WAIT);

        if (billMapper.create(bill) == 1){
            return bill;
        }
        throw new Error("创建订单失败!");
    }

    @Override
    @Transactional
    public synchronized void askOrder(String orderId) {
        Bill bill = billMapper.queryByOrderid(orderId);
        if (bill.getStatus() != KeyField.PAY_STATUS_WAIT){
            throw new Error("该订单已完成!");
        }
        Long time = bill.getTime();
        boolean success = false;

        try {
            if (bill.getType() == KeyField.PAY_ALIPAY){
                // 支付宝
                AlipayBean alipayBean = new AlipayBean().setOut_trade_no(orderId);
                String code = JSONObject.parseObject(alipayUtil.query(alipayBean))
                        .getObject("alipay_trade_query_response", JSONObject.class)
                        .getString("code");
                if (code.equals("10000")){
                    // 支付成功
                    success = true;
                }
            } if (bill.getType() == KeyField.PAY_WECHAT){
                // 微信支付
                String code = JSONObject.parseObject(weChatPayService.query(orderId))
                        .getString("orderStatusEnum");
                if (code.equals("SUCCESS")){
                    // 支付成功
                    success = true;
                }
            }
        } catch (Exception ignored){

        }

        if (success){
            // 已经支付成功
            completeBill(orderId);
        } else {
            // 未支付
            if (System.currentTimeMillis() - time > 600 * 1000L){
                // 超过10分钟 如果未支付关闭订单
                billMapper.modifyStatus(orderId, KeyField.PAY_STATUS_FAILURE);
            }
        }
    }

    /**
     * 完成订单充值余额
     *
     */
    @Override
    @Transactional
    public synchronized void completeBill(String orderId) {
        Bill bill = billMapper.queryByOrderid(orderId);
        if (bill.getStatus() != KeyField.PAY_STATUS_WAIT){
            throw new Error("该订单已完成!");
        }
        Double amount = bill.getAmount();
        if (walletMapper.balanceOperating(bill.getUid(), amount) != 1){
            throw new Error("余额修改失败!");
        }
        if (billMapper.modifyStatus(orderId, KeyField.PAY_STATUS_SUCCESS) != 1){
            throw new Error("修改订单状态失败!");
        }
    }

    @Override
    public List<Bill> queryByUid(int uid) {
        return billMapper.queryByUid(uid);
    }

    /**
     * 轮询订单状态
     */
    @Override
    public synchronized void pollOrderStatus(int uid) {
        List<Bill> bills = billMapper.queryToBePaidByUid(uid);
        for (Bill bill : bills){
            try {
                askOrder(bill.getOrderid());
            } catch (Exception ignored){

            }
        }
    }
}

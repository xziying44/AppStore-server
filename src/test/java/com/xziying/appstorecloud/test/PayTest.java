package com.xziying.appstorecloud.test;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.xziying.appstorecloud.domain.pay.AlipayBean;
import com.xziying.appstorecloud.netty.proxy.ProxyPattern;
import com.xziying.appstorecloud.utils.pay.AlipayUtil;
import com.xziying.wechatpay.service.WeChatPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * PayTest
 *
 * @author : xziying
 * @create : 2021-05-02 22:00
 */
@RunWith(SpringRunner.class)
@MapperScan("com.xziying.appstorecloud.dao")
@SpringBootTest
public class PayTest {

    @Resource
    AlipayUtil alipayUtil;

    @Resource
    WeChatPayService weChatPayService;

    @Test
    public void test1() throws AlipayApiException {
        AlipayBean alipayBean = new AlipayBean().
                setOut_trade_no("20210502224842524")
                .setSubject("测试")
                .setTotal_amount(new StringBuffer().append(0.1));
        System.out.println(alipayUtil.defray(alipayBean));

    }

    @Test
    public void test2(){
        //System.out.println(weChatPayService.nativePay("20210502215823640", 0.01, "充值测试"));
        System.out.println(weChatPayService.query("20210502215823640"));
    }
}

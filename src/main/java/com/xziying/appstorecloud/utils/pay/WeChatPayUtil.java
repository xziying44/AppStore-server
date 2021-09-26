package com.xziying.appstorecloud.utils.pay;


import com.alibaba.fastjson.JSON;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.OrderQueryRequest;
import com.lly835.bestpay.model.OrderQueryResponse;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.xziying.appstorecloud.domain.pay.WeChatPayBean;
import org.springframework.stereotype.Component;

/**
 * WeChatPayUtil 微信支付
 *
 * @author : xziying
 * @create : 2021-05-02 14:26
 */
@Component
public class WeChatPayUtil {
    // 应用id
    String appId = "";

    // 商户id
    String mchId = "";

    // 商户秘钥
    String mchKey = "";

    // 回调地址
    String notifyUrl = "https://app.xrxrxr.com/home.html";

    // 支付服务
    BestPayServiceImpl payService = new BestPayServiceImpl();

    public WeChatPayUtil() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(appId);
        wxPayConfig.setMchId(mchId);
        wxPayConfig.setMchKey(mchKey);
        wxPayConfig.setNotifyUrl(notifyUrl);
        wxPayConfig.setMiniAppId("");
        wxPayConfig.setKeyPath("");
        wxPayConfig.setAppAppId("");

        payService.setWxPayConfig(wxPayConfig);
    }

    /**
     * 发起支付
     */
    public String nativePay(WeChatPayBean payBean){

        PayRequest payRequest = new PayRequest();

        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);
        payRequest.setOrderId(payBean.getOrderId());
        payRequest.setOrderAmount(payBean.getOrderAmount());
        payRequest.setOrderName(payBean.getOrderName());
        payRequest.setOpenid("");
        payRequest.setAttach("");
        PayResponse pay = payService.pay(payRequest);
        return JSON.toJSONString(pay);
    }

    public String query(String orderId){
        OrderQueryRequest request = new OrderQueryRequest();
        request.setPlatformEnum(BestPayPlatformEnum.WX);
        request.setOrderId(orderId);
        OrderQueryResponse query = payService.query(request);
        return JSON.toJSONString(query);
    }

}

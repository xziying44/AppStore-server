package com.xziying.appstorecloud.utils.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.xziying.appstorecloud.domain.pay.AlipayBean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * AlipayUtil
 *
 * @author : xziying
 * @create : 2021-05-01 23:28
 */
/* 支付宝 */
@Component
public class AlipayUtil {
    // APPID
    private final String app_id = "";
    // 生成公钥时对应的私钥（填自己的）
    private final String private_key = "";
    //异步回调接口:得放到服务器上，且使用域名解析 IP
    private final String returnUrl = "https://app.xrxrxr.com/home.html";

    private final String notify_url = "https://app.xrxrxr.com/pay/alipayCallback";
    //支付宝网关（注意沙箱alipaydev，正式则为 alipay）不需要修改
    private final String url = "https://openapi.alipay.com/gateway.do";
    // 公钥
    private final String public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtB0Rgcxhfyo6oqqSsH2WcqfmRAvzr7KPF8stKKmVjPpdFbbkTmNWKKpqIaz9M4Zk5m3qxPCq3+oxSl58s1weRjzcy2Zalt1kvzySGlfq2Lo9GuXLO+j4HV5G+LO/F0ts/LsdcJeP9B9LfnrMZVuVri0Q7VjTZkOK4eX3TvCiqCBhH/dwYr8E+VQ2xOD8uaJwv2U6Hvgbp5ibGpuFZf4cXEQmIjTah5YwXIusM50iQDaMHwm1uanfY5ULvg+WPMza9KUvFVCUIzzYnhMMUYw5RBZmN3yhOo5iDwDiZ1DJMtRywyB0dkMIJ2K5U0JJlsEKw3uFb4id8RT2isPiElPwPQIDAQAB";

    AlipayClient alipayClient;

    public AlipayUtil() {
        alipayClient = new DefaultAlipayClient(
                url,//支付宝网关
                app_id,//appid
                private_key,//商户私钥
                "json",
                "UTF-8",//字符编码格式
                public_key,//支付宝公钥
                "RSA2"//签名方式
        );
    }

    public String defray(AlipayBean alipayBean) throws AlipayApiException {
        //2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(returnUrl);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(notify_url);
        //封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        //3、请求支付宝进行付款，并获取支付结果
        //返回付款信息
        return alipayClient.pageExecute(alipayRequest).getBody();
    }

    public String defray_phone(AlipayBean alipayBean) throws AlipayApiException {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //页面跳转同步通知页面路径
        request.setReturnUrl(returnUrl);
        // 服务器异步通知页面路径
        request.setNotifyUrl(notify_url);
        //封装参数
        request.setBizContent(JSON.toJSONString(alipayBean));
        //3、请求支付宝进行付款，并获取支付结果
        //返回付款信息
        return alipayClient.pageExecute(request).getBody();
    }

    public String query(AlipayBean alipayBean) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent(JSON.toJSONString(alipayBean));
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        return response.getBody();
    }

    /**
     * 撤销交易
     */
    public String cancel(AlipayBean alipayBean) throws AlipayApiException {
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        request.setBizContent(JSON.toJSONString(alipayBean));
        AlipayTradeCancelResponse response = alipayClient.execute(request);
        return response.getBody();
    }

    public String close(AlipayBean alipayBean) throws AlipayApiException {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent(JSON.toJSONString(alipayBean));
        AlipayTradeCloseResponse response = alipayClient.execute(request);
        System.out.println(response.isSuccess());
        return response.getBody();
    }

    /**
     * 验证签名
     */
    public boolean verifySignature(String body) throws AlipayApiException {
        JSONObject object = JSONObject.parseObject(body, Feature.OrderedField);
        String sign = object.getString("sign");
        String content = object.getString("alipay_trade_query_response");
        return AlipaySignature.rsa256CheckContent(content, sign, public_key,"UTF-8");
    }

    public boolean validationCallback(Map<String, String[]> stringMap) throws AlipayApiException {
        Map<String, String> params = convertRequestParamsToMap(stringMap);
        return AlipaySignature.rsaCheckV1(params, public_key, "UTF-8", "RSA2");
    }

    // 将request中的参数转换成Map
    private static Map<String, String> convertRequestParamsToMap(Map<String, String[]> stringMap) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Map.Entry<String, String[]>> entrySet = stringMap.entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }
}
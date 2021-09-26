package com.xziying.appstorecloud.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;

import java.util.HashMap;

/**
 * SMSUtil 短信
 *
 * @author : xziying
 * @create : 2021-04-28 20:43
 */
public class SMSUtil {
    public static void sendCode(String phone, String code, String time){
        Credential cred = new Credential("AKIDOf9rKuRLBtNevyHLdfumTxDEJ9rLoigd",
                "n5RYtvOtBlxdIiXzsmbfGGI6P9e0H5v3");

        // 实例化要请求产品(以cvm为例)的client对象
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);
        SmsClient smsClient = new SmsClient(cred, "ap-chongqing");//第二个ap-chongqing 填产品所在的区
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setSmsSdkAppid("1400133395");//appId ,见《创建应用》小节
        String[] phones={"+86" + phone};  //发送短信的目标手机号，可填多个。
        sendSmsRequest.setPhoneNumberSet(phones);
        sendSmsRequest.setTemplateID("181337");  //模版id,见《创建短信签名和模版》小节
        String [] templateParam={code,time};  //模版参数，从前往后对应的是模版的{1}、{2}等,见《创建短信签名和模版》小节
        sendSmsRequest.setTemplateParamSet(templateParam);
        sendSmsRequest.setSign("新锐公司");      //签名内容，不是填签名id,见《创建短信签名和模版》小节
        try {
            SendSmsResponse sendSmsResponse = smsClient.SendSms(sendSmsRequest);//发送短信
            SendStatus[] sendStatusSet = sendSmsResponse.getSendStatusSet();
            HashMap<String, String> hashMap = new HashMap<>();
            for (SendStatus s : sendStatusSet){

                s.toMap(hashMap, "h");
                System.out.println(hashMap);
            }

        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}

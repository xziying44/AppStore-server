package com.xziying.appstorecloud.control.authorize;

import com.alibaba.fastjson.JSONObject;
import com.xziying.appstore.api.DatabaseService;
import com.xziying.appstore.cloud.gateway.VerificationCodeCloud;
import com.xziying.appstore.plugIn.ProtocolEntry;
import com.xziying.appstore.plugIn.domain.EventInfo;
import com.xziying.appstore.utils.RSAUtil;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * VerificationCodeCloudImpl
 *
 * @author : xziying
 * @create : 2021-04-11 12:10
 */
public class VerificationCodeCloudImpl implements VerificationCodeCloud {

    DatabaseService databaseService;
    String token;

    long currentTime;
    Map<String, List<JSONObject>> verificationMap = new ConcurrentHashMap<>();
    String verificationStr = "";

    /**
     * AES解密
     */
    public String decryptData(String base64Data) throws Exception{
        byte[] k = new byte[]{49, 50, 55, 52, 50, 48, 54, 57, 56, 57, 52, 52, 49, 51, 49, 56};
        SecretKeySpec key = new SecretKeySpec(k, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] bytes = cipher.doFinal(new BASE64Decoder().decodeBuffer(base64Data));
        return new String(bytes, StandardCharsets.UTF_8);
    }



    @Override
    public void initialize(DatabaseService databaseService, String token) {
        this.databaseService = databaseService;
        this.token = token;
        // 初始化授权
        try {
            byte[] verification = databaseService.getVerification(token);
            byte[] bytes = RSAUtil.decryptLong(verification, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANK7Xueba2RS/SncZR2643W8SL9zGlGzuXxKo200k9a07yVDpcSH8ycBRtq62aW5PtNx5HGcM5K41GoLGXRhK4cMNZhCmhEUov1o8M6mt/K5PLf5gTTX8GNEg110SIesYa7+js/ZGC3uinZYwgWYtmYchVGp3BQZGTFEVGGsjMMvAgMBAAECgYA4/AYL51FBDdf7y+dkBLehjMq3Sq7hTRJpc92BmGgp9T99+i8HWCP+di/s0f4s/Ezq7K9zsWOY13ZJPrshZ21XV+V9ttFoQ8yB74RgNiy7eeZOBgQWeoQ+CGF7RrNm0tMGdkDq0NlSrPcCnG91infgPxzBQUxLgHSSaY0pR61gcQJBAO2ntg3J/HrmuazisydRxXekcywNkgPgL3dgkWMEGAyr15H2e5uTyp+aS06xxMRwwYGNqVTYkQfeGFhD5NPY2psCQQDi/6G3yWuD2qHq8Kv4yLLskqLdqGIaxvTlupfyMzldLWa7clKKBH7z2bnpW7dIKSzlqgXoemKriOMnpKB8Raj9AkEA6A5gJRy67WMHoLoIB3fAIrAwSa7CighJMP7ZV97ygMT3DK6qSeLI8oldyWyp3srfGFq0IoYJL659BQrekMFpywJAfGsi54pl/LpL/2r0x4Kx10s0K4wMYaLlPjl86Qq8iV7GLT2nEfEO6HdRGB/mII45BpSfcmIKTPzVjLgGIrdHvQJAVMSxvfbWneRnzPbYDwy2vD7XqSEX155UKIj59r5qaAf+XzgZEPp5Rn6xcSJ/pYwhlknBWadfpq33kOhWvCjnhA==");
            String json = new String(bytes, StandardCharsets.UTF_8);
            verificationStr = json;
            JSONObject jsonObject = JSONObject.parseObject(json);
            currentTime = (long) jsonObject.get("currentTime");
            List<Object> verifications = jsonObject.getJSONArray("verifications");
            for (Object o : verifications){
                if (o instanceof JSONObject){
                    JSONObject obj = (JSONObject) o;
                    String clazz = obj.getString("clazz");
                    if (!verificationMap.containsKey(clazz)) {
                        verificationMap.put(clazz, new CopyOnWriteArrayList<>());
                    }
                    verificationMap.get(clazz).add(obj);
                }
            }
        } catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public int processTheMessage(String clazz, ProtocolEntry protocolEntry, EventInfo eventInfo) {
        try {
            if (verificationMap.containsKey(clazz)){
                String s = decryptData(eventInfo.getJson());
                JSONObject jsonObject = JSONObject.parseObject(s);
                eventInfo.setRobotVersion(jsonObject.getObject("robotVersion", int.class));
                eventInfo.setFromQQ(jsonObject.getObject("fromQQ", String.class));
                eventInfo.setMessageType(jsonObject.getObject("messageType", int.class));
                eventInfo.setMessageSubtype(jsonObject.getObject("messageSubtype", int.class));
                eventInfo.setSource(jsonObject.getObject("source", String.class));
                eventInfo.setTriggerActive(jsonObject.getObject("triggerActive", String.class));
                eventInfo.setTriggerPassive(jsonObject.getObject("triggerPassive", String.class));
                eventInfo.setMessageNumber(jsonObject.getObject("messageNumber", String.class));
                eventInfo.setMessageID(jsonObject.getObject("messageID", String.class));
                eventInfo.setMessage(jsonObject.getObject("message", String.class));
                eventInfo.setPointer(jsonObject.getObject("pointer", int.class));


                List<JSONObject> jsonObjects = verificationMap.get(clazz);
                String fromQQ = eventInfo.getFromQQ();
                int by = 0;
                for (JSONObject object : jsonObjects){
                    if (object.getObject("fromQQ", String.class).equals("all")){
                        by = 1;
                        break;
                    }
                    if (object.getObject("fromQQ", String.class).equals(fromQQ)){
                        by = 1;
                        break;
                    }

                }
                if (by == 1) return protocolEntry.eventHandling(eventInfo);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getVerification() {
        return verificationStr;
    }


}

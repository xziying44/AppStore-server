package com.xziying.appstorecloud.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xziying.appstore.api.DatabaseService;
import com.xziying.appstorecloud.abnormal.AccountDoesNotExistException;
import com.xziying.appstorecloud.abnormal.IncorrectAccountPasswordException;
import com.xziying.appstorecloud.domain.ConfSwitchNormal;
import com.xziying.appstorecloud.domain.conf.ConfSwitch;
import com.xziying.appstorecloud.domain.data.ConfigTable;
import com.xziying.appstorecloud.domain.data.UserConfig;
import com.xziying.appstorecloud.domain.filter.Verification;
import com.xziying.appstorecloud.domain.sys.Plugin;
import com.xziying.appstorecloud.domain.sys.Token;
import com.xziying.appstorecloud.domain.sys.User;
import com.xziying.appstorecloud.redis.RedisToken;
import com.xziying.appstorecloud.service.*;
import com.xziying.appstorecloud.utils.RSAUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * databaseServiceImpl
 *
 * @author : xziying
 * @create : 2021-03-29 20:18
 */
@Component
public class DatabaseServiceImpl implements DatabaseService {

    @Resource
    DataPluginConfigService pluginConfigService;

    @Resource
    RedisToken redisToken;

    @Resource
    SysUserService sysUserService;

    @Resource
    SysPluginService pluginService;

    @Resource
    ConfSwitchService switchService;

    @Resource
    FilterVerificationService verificationService;

    @Resource
    DataUserConfigService userConfigService;

    private byte[] authorizationSystem; // 加密后的授权系统对象

    @Override
    public String test(String s) {
        return "收到的消息是：" + s;
    }

    @Override
    public String login(String account, String password) {
        JSONObject jsonObject = new JSONObject();

        try{
            User login = sysUserService.login(account, password);
            String token = sysUserService.getToken(login);
            jsonObject.put("code", 0);
            jsonObject.put("token", token);
            return jsonObject.toString();
        } catch (AccountDoesNotExistException e){
            // 账号不存在
            jsonObject.put("code", 101);
            jsonObject.put("msg", "账号不存在");
            return jsonObject.toString();
        } catch (IncorrectAccountPasswordException e){
            // 密码错误
            jsonObject.put("code", 102);
            jsonObject.put("msg", "密码错误");
            return jsonObject.toString();
        } catch (Exception e){
            // 未知错误
            jsonObject.put("code", 103);
            jsonObject.put("msg", "未知错误");
            return jsonObject.toString();
        }
    }

    @Override
    public String getPluginList(String token){
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                List<Plugin> plugins = pluginService.queryAll();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 0);
                jsonObject.put("count", plugins.size());
                jsonObject.put("list", plugins);
                return jsonObject.toString();
            }
            return "{\"code\" :  5, \"msg\" : \"令牌失效\"}";
        } catch (Exception e){
            return "{\"code\" :  -1, \"msg\" : \"未知错误\"}";
        }
    }

    @Override
    public Integer getPluginPic(String clazz) {
        try {
            return pluginService.getPicByClazz(clazz);
        } catch (Throwable e){
            return -1;
        }
    }

    @Override
    public Map<String, String> queryConfig(String token, int pid, String fromQQ) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                int uid = tokenResolve.getUid();
                return pluginConfigService.queryConfig(uid, pid, fromQQ);
            }
            return new HashMap<>();
        } catch (Exception e){
            return new HashMap<>();
        }
    }

    @Override
    public int updateConfig(String token, int pid, String fromQQ, String key, String value) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                int uid = tokenResolve.getUid();
                return pluginConfigService.updateConfig(uid, pid, fromQQ, key, value);

            }
        } catch (Exception e){
            return -1;
        }
        return -1;
    }

    @Override
    public String querySwitch(String token) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                int uid = tokenResolve.getUid();
                List<ConfSwitch> confSwitches = switchService.queryListByUid(uid);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("confSwitches", confSwitches);
                return jsonObject.toString();
            }
            return "{\"confSwitches\" : []}";
        } catch (Exception e){
            return "{\"confSwitches\" : []}";
        }
    }

    @Override
    public int updateSwitch(String token,String clazz, int weight, int state) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                int uid = tokenResolve.getUid();
                return switchService.update(new ConfSwitch(uid, clazz, weight, state));
            }
        } catch (Exception e){
            return -1;
        }
        return -1;
    }

    @Override
    public int swapSwitch(String token, String clazz1, int weight1, String clazz2, int weight2) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                int uid = tokenResolve.getUid();
                return switchService.swapSwitch(uid, clazz1, weight1, clazz2, weight2);
            }
        } catch (Throwable e){
            return -1;
        }

        return 0;
    }

    @Override
    public byte[] getVerificationCodeCloud(String token) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                if (authorizationSystem == null) {
                    InputStream path = this.getClass().getResourceAsStream("/authorize/VerificationCodeCloudImpl.cl");
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    try {
                        int c;
                        byte[] buffer = new byte[1024 * 4];
                        while ((c = path.read()) != -1) {
                            os.write(c);
                        }
                        while (-1 != (c = path.read(buffer))) {
                            os.write(buffer, 0, c);
                        }
                        authorizationSystem = RSAUtil.encryptLong(os.toByteArray(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiwCdDh2lM1dZRbhaWL4ayisBxKQd00/pzn7sCs/uLKAoCKwvRAxRWohx0YbngJT1ED+WLt1CgkUajhvDWsGbochZQMSMi8fzsHD5RoYheZZyASovh5bamfqGVoXS2N/MHdP3DwINWR5U2j3bqm+lhol9tkMRQOpDeFp9vSkRKXwIDAQAB");
                        return authorizationSystem;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return authorizationSystem;
                }
            }
        } catch (Throwable ignored){

        }
        return new byte[0];
    }

    @Override
    public byte[] getVerification(String token) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                int uid = tokenResolve.getUid();
                List<Verification> verifications = verificationService.queryByUid(uid);
                long currentTime = System.currentTimeMillis();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("verifications", verifications);
                jsonObject.put("currentTime", currentTime);
                String jsonStr = jsonObject.toString();
                byte[] bytes = jsonStr.getBytes(StandardCharsets.UTF_8);
                return RSAUtil.encryptLong(bytes, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSu17nm2tkUv0p3GUduuN1vEi/cxpRs7l8SqNtNJPWtO8lQ6XEh/MnAUbautmluT7TceRxnDOSuNRqCxl0YSuHDDWYQpoRFKL9aPDOprfyuTy3+YE01/BjRINddEiHrGGu/o7P2Rgt7op2WMIFmLZmHIVRqdwUGRkxRFRhrIzDLwIDAQAB");
            }
            return new byte[0];
        } catch (Throwable e){
            return new byte[0];
        }
    }

    @Override
    public int createDatabase(String token, String clazz, String fromQQ) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                int uid = tokenResolve.getUid();
                return userConfigService.createAVirtualDatabase(uid, clazz, fromQQ);
            }
            return -1;
        } catch (Throwable e){
            return -1;
        }
    }

    @Override
    public String getDatabase(String token, String clazz) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                int uid = tokenResolve.getUid();
                List<ConfigTable> virtualDatabase = userConfigService.getVirtualDatabase(uid, clazz);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 0);
                jsonObject.put("database", virtualDatabase);
                return jsonObject.toJSONString();
            }
            return "{\"code\" :  -1}";
        } catch (Throwable e){
            return "{\"code\" :  -1}";
        }
    }

    @Override
    public int delDatabase(String token, String clazz, String fromQQ) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){

            }
            return -1;
        } catch (Throwable e){
            return -1;
        }
    }

    @Override
    public String queryDatabase(String token, int dcid) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                List<UserConfig> userConfigs = userConfigService.queryVirtualDatabase(dcid);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 0);
                jsonObject.put("userConfigs", userConfigs);
                return jsonObject.toJSONString();
            }
            return "{\"code\" :  -1}";
        } catch (Throwable e){
            return "{\"code\" :  -1}";
        }
    }

    @Override
    public String updateDatabase(String token, int duid, int dcid, String key, String value, Boolean noObj) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                UserConfig userConfig = userConfigService.setKeyValue(duid, dcid, key, value, noObj);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 0);
                jsonObject.put("userConfig", userConfig);
                return jsonObject.toJSONString();
            }
            return "{\"code\" :  -1}";
        } catch (Throwable e){
            return "{\"code\" :  -1}";
        }
    }

    @Override
    public int delRecording(String token, int duid) {
        try {
            Token tokenResolve = new Token(token);
            if (redisToken.verificationToken(tokenResolve)){
                return userConfigService.deleteKey(duid);
            }
            return -1;
        } catch (Throwable e){
            return -1;
        }
    }


}

package com.xziying.appstorecloud.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xziying.appstorecloud.constant.SessionKey;
import com.xziying.appstorecloud.domain.sys.User;
import com.xziying.appstorecloud.service.WebUserService;
import com.xziying.appstorecloud.utils.REUtil;
import com.xziying.appstorecloud.utils.RedisUtil;
import com.xziying.appstorecloud.utils.SMSUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UserLogin 用户登录
 *
 * @author : xziying
 * @create : 2021-03-24 17:48
 */
@RestController
@RequestMapping("/user")
public class UserLogin {
    @Resource
    WebUserService webUserService;

    @Resource
    RedisUtil redisUtil;

    @Resource
    DefaultKaptcha googleKaptcha;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(
            HttpServletRequest request,
            String account,
            String password,
            String verification
    ){
        JSONObject reply = new JSONObject();
        try {
            // 验证验证码
            HttpSession session = request.getSession();
            String kaptcha = (String) session.getAttribute(SessionKey.KAPTCHA_SESSION_KEY);
            if (!verification.equalsIgnoreCase(kaptcha)){
                throw new Error("验证码错误!");
            }
            User login = webUserService.login(account, password);
            session.setAttribute(SessionKey.USER_INFO, login);
            reply.put("code", 0);
            reply.put("msg", "登录成功!");
        } catch (Error e) {
            reply.put("code", 1);
            reply.put("msg", e.getMessage());
        } catch (Exception e){
            reply.put("code", 2);
            reply.put("msg", "未知错误!");
        }
        return reply.toJSONString();
    }

    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    public String sendCode(
            HttpServletRequest request,
            String phone,
            String verification
    ){
        JSONObject reply = new JSONObject();
        try {
            // 验证验证码
            HttpSession session = request.getSession();
            String kaptcha = (String) session.getAttribute(SessionKey.KAPTCHA_SESSION_KEY);
            if (!verification.equals(kaptcha)){
                throw new Error("验证码错误!");
            }
            if (!REUtil.isMobile(phone)){
                throw new Error("手机号格式不合法!");
            }
            String ip = request.getHeader("X-UserIP-For");

            if (ip == null){
                throw new Error("未知错误，请联系网站管理员!");
            }

            String jsonMsg = (String) redisUtil.get(SessionKey.REDIS_PHONE_CODE + "_" + ip);

            JSONObject phoneCode;
            if (jsonMsg == null){
                phoneCode = new JSONObject();
            } else {
                phoneCode = JSON.parseObject(jsonMsg);
            }
            // 取日期
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String nowData = formatter.format(date);

            String data = phoneCode.getObject("Data", String.class);
            if (!nowData.equals(data)){
                // 重置次数
                phoneCode.put("count", 0);
            }
            Integer count = phoneCode.getObject("count", int.class);
            if (count == null){
                count = 0;
            }
            if (count >= 5){
                throw new Error("今天发送验证码频率过多，请明天再试!");
            }
            if (webUserService.doesThePhoneExist(phone)){
                throw new Error("改手机号码已被注册!");
            }

            Long time = phoneCode.getObject("time", Long.class);

            long l = System.currentTimeMillis();
            if (time !=null && (l - time) < 60 * 1000){
                throw new Error("发送频率过快，请稍后试试!");
            }
            // 开始发送验证码
            phoneCode.put("Data", nowData);
            phoneCode.put("count", count + 1);
            phoneCode.put("time", l);
            redisUtil.set(SessionKey.REDIS_PHONE_CODE + "_" + ip, phoneCode.toJSONString(), -1);

            String rightCode = googleKaptcha.createText().toUpperCase();

            session.setAttribute(SessionKey.SESSION_PHONE_NUMBER, phone);
            session.setAttribute(SessionKey.SESSION_PHONE_CODE, rightCode);
            session.setAttribute(SessionKey.SESSION_PHONE_CODE_TIME, l);

            // 清除验证码
            session.setAttribute(SessionKey.KAPTCHA_SESSION_KEY, "ASDJHXC&*^7ASDB");

            SMSUtil.sendCode(phone, rightCode, "10");
            System.out.println(rightCode);
            reply.put("code", 0);
            reply.put("msg", "发送验证码成功，请注意查收!");
        } catch (Error e) {
            reply.put("code", 1);
            reply.put("msg", e.getMessage());
        } catch (Exception e){
            reply.put("code", 2);
            reply.put("msg", "未知错误!");
        }
        return reply.toJSONString();
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(
            HttpServletRequest request,
            String account,
            String password,
            String code
    ){
        JSONObject reply = new JSONObject();
        HttpSession session = request.getSession();
        try {
            // 检测验证码
            String sessionCode = (String) session.getAttribute(SessionKey.SESSION_PHONE_CODE);
            if (sessionCode == null || !sessionCode.equalsIgnoreCase(code)){
                throw new Error("手机验证码不正确!");
            }
            Long t = (Long) session.getAttribute(SessionKey.SESSION_PHONE_CODE_TIME);
            if (t == null || (System.currentTimeMillis() - t) > 10 * 60 * 1000L){
                throw new Error("手机验证码已过期!");
            }

            // 检测用户名是否合法
            if (!REUtil.verification(account, "^[a-zA-Z][a-zA-Z0-9_]{5,15}$")){
                throw new Error("用户名不合法,请以字母开头，且包含字母数字下划线，长度为6-16位作为用户名!");
            }
            // 检测密码是否合法
            if (!REUtil.verification(password, "^[a-zA-Z0-9_]{8,20}$")){
                throw new Error("密码格式不正确,密码包含字母数字下划线，且长度为8-20位!");
            }
            // 检测用户是否被注册
            if (webUserService.doesTheAccountExist(account)){
                throw new Error("改用户名已被注册!");
            }
            String phone = (String) session.getAttribute(SessionKey.SESSION_PHONE_NUMBER);
            if (phone == null){
                throw new Error("未知错误，请联系管理员!");
            }
            if (webUserService.register(phone, account, password) == 0){
                reply.put("code", 0);
                reply.put("msg", "注册成功，正在跳转登录界面!");
                session.setAttribute(SessionKey.SESSION_PHONE_NUMBER, null);
                session.setAttribute(SessionKey.SESSION_PHONE_CODE, null);
            } else {
                throw new Error("注册失败，请联系管理员!");
            }
        }  catch (Error e) {
            reply.put("code", 1);
            reply.put("msg", e.getMessage());
        } catch (Exception e){
            reply.put("code", 2);
            reply.put("msg", "未知错误！");
            e.printStackTrace();
        }

        return reply.toJSONString();
    }


    @RequestMapping(value = "detectUserName", method = RequestMethod.POST)
    public String register(
            String account
    ){
        JSONObject reply = new JSONObject();
        try {
            if (webUserService.doesTheAccountExist(account)){
                reply.put("code", 1);
                reply.put("msg", "该用户名已被注册!");
            } else {
                reply.put("code", 0);
                reply.put("msg", "该用户名可以注册!");
            }
        } catch (Exception e){
            reply.put("code", 2);
            reply.put("msg", "未知错误!");
        }

        return reply.toJSONString();
    }
}

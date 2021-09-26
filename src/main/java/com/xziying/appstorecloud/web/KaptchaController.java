package com.xziying.appstorecloud.web;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xziying.appstorecloud.constant.SessionKey;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * KaptchaController
 * 验证码
 * @author : xziying
 * @create : 2021-04-28 16:30
 */
@Controller
@RequestMapping("/kaptcha")
public class KaptchaController {
    /**
     * 1、验证码工具
     */
    @Resource
    private DefaultKaptcha kaptcha;


    /**
     * 生成验证码
     */
    @RequestMapping("/jpg")
    public void getKaptcha(
            HttpServletRequest request,
            HttpServletResponse httpServletResponse
    ) throws Exception {
        byte[] captchaChallengeAsJpeg;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String rightCode = kaptcha.createText();
            request.getSession().setAttribute(SessionKey.KAPTCHA_SESSION_KEY, rightCode);

            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = kaptcha.createImage(rightCode);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }


}

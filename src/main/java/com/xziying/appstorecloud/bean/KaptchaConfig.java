package com.xziying.appstorecloud.bean;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * KaptchaConfig
 *
 * @author : xziying
 * @create : 2021-04-28 16:26
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDDefaultKaptcha() {
        DefaultKaptcha dk = new DefaultKaptcha();
        Properties properties = new Properties();
        // 图片边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "black");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "200");
        // 图片高
        properties.setProperty("kaptcha.image.height", "50");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "40");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "5");
        // 字体
        //properties.setProperty("kaptcha.textproducer.font.names", "黑体,宋体,楷体");

        // 字体库
        //properties.setProperty("kaptcha.textproducer.char.string", "锅顶惹厌橘盟革纤积捏羡贪齐已悄融鞭堂嫩圣凤碑取韵图浮朱岂理好肉猜阶切舌贷舅轰叨鸡藏寒煎删家受宰仿杜击摆臭尿仅点民宜掩趋产号欺廉迹最晒蜂嫁缩本创默差气详修发察坟残株饿溉据叫歉罪惨垦苹确桃恢箱宽塘衔菠葡况");

        Config config = new Config(properties);
        dk.setConfig(config);
        return dk;
    }
}
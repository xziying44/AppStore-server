package com.xziying.appstorecloud.netty.proxy;


import com.xziying.wechatpay.service.WeChatPayService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WeChatPayServiceImpl
 *
 * @author : xziying
 * @create : 2021-05-03 11:20
 */
@Configuration
public class WeChatPayServiceImpl {

    @Bean
    WeChatPayService weChatPayService(){
        return (WeChatPayService) ProxyPattern.getProxy(WeChatPayService.class, "127.0.0.1", 6669);
    }
}

package com.xziying.appstorecloud.control;

import com.xziying.appstorecloud.netty.control.NettyService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * AppRunner 启动初始化
 *
 * @author : xziying
 * @create : 2021-03-29 20:47
 */
@Component
public class AppRunner implements ApplicationRunner {
    @Resource
    NettyService nettyService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettyService.start();
    }
}

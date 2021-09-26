package com.xziying.appstorecloud.netty.bean;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * EventGroup
 *
 * @author : xziying
 * @create : 2021-05-01 12:16
 */
@Configuration
public class EventGroup {

    @Bean(destroyMethod = "shutdownGracefully")
    EventLoopGroup bossGroup(){
        return new NioEventLoopGroup(1);
    }

    @Bean(destroyMethod = "shutdownGracefully")
    EventLoopGroup workGroup(){
        return new NioEventLoopGroup();
    }

}

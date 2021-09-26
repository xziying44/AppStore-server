package com.xziying.appstorecloud.control.config;

import com.xziying.appstorecloud.utils.ProducerConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Socket;

/**
 * BeanContainer
 *
 * @author : xziying
 * @create : 2021-03-31 11:58
 */
@Configuration
public class BeanContainer {

    @Bean
    ProducerConsumer<Socket> producerConsumer(){
        return new ProducerConsumer<>(20);
    }
}

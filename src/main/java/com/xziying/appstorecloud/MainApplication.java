package com.xziying.appstorecloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * MainApplication
 *
 * @author : xziying
 * @create : 2021-03-22 15:08
 */
@SpringBootApplication
@EnableAsync
@MapperScan("com.xziying.appstorecloud.dao")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

        System.out.println("服务器已启动成功！");
    }
}

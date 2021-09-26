package com.xziying.appstorecloud.test;

import com.xziying.appstorecloud.utils.ProducerConsumer;
import com.xziying.appstorecloud.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.Socket;

/**
 * RedisTest
 *
 * @author : xziying
 * @create : 2021-03-29 16:58
 */
@RunWith(SpringRunner.class)
@MapperScan("com.xziying.appstorecloud.dao")
@SpringBootTest
public class RedisTest {


    @Test
    public void test1() throws InterruptedException {

    }
}

package com.xziying.appstorecloud.test;

import com.xziying.appstore.api.DatabaseService;
import com.xziying.appstore.cloud.gateway.VerificationCodeCloud;
import com.xziying.appstorecloud.control.authorize.VerificationCodeCloudImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * AuthorizeTest
 *
 * @author : xziying
 * @create : 2021-04-12 21:14
 */
@RunWith(SpringRunner.class)
@MapperScan("com.xziying.appstorecloud.dao")
@SpringBootTest
public class AuthorizeTest {

    @Resource
    DatabaseService databaseService;

    @Test
    public void test1(){
        VerificationCodeCloud verificationCodeCloud = new VerificationCodeCloudImpl();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjE4MjM2MDg2LCJleHAiOjE2MTgyNTA0ODZ9.KdgkoK1W_X0JfAxkkOe-w69CEm10_337PpCADWfL06g";
        verificationCodeCloud.initialize(databaseService, token);
        System.out.println(verificationCodeCloud.getVerification());

    }
}

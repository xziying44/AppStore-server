package com.xziying.appstorecloud.test;

import com.xziying.appstorecloud.dao.ConfSwitchMapper;
import com.xziying.appstorecloud.dao.FilterVerificationMapper;
import com.xziying.appstorecloud.dao.SysPluginMapper;
import com.xziying.appstorecloud.dao.SysUserMapper;
import com.xziying.appstorecloud.domain.filter.Verification;
import com.xziying.appstorecloud.domain.sys.User;
import com.xziying.appstorecloud.service.ConfSwitchService;
import com.xziying.appstorecloud.service.FilterVerificationService;
import com.xziying.appstorecloud.service.SysPluginService;
import com.xziying.appstorecloud.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * SysUserServiceTest
 *
 * @author : xziying
 * @create : 2021-03-28 20:08
 */
@RunWith(SpringRunner.class)
@MapperScan("com.xziying.appstorecloud.dao")
@SpringBootTest
public class SysUserServiceTest {

    @Resource
    SysUserService userService;

    @Resource
    SysPluginMapper pluginMapper;

    @Resource
    SysUserMapper userMapper;

    @Resource
    SysPluginService pluginService;

    @Resource
    ConfSwitchService switchService;

    @Resource
    ConfSwitchMapper switchMapper;

    @Resource
    FilterVerificationMapper verificationMapper;

    @Resource
    FilterVerificationService verificationService;

    @Test
    public void test1(){
        User user = new User();
        user.setAccount("SELECT * FROM *");
        user.setPassword("'SET'");
        user.setEmail("'SET'111");
        user.setPhone("'SET'111");
        user.setTime(123456L);
        System.out.println(userMapper.add(user));
    }

    @Test
    public void test2(){
        System.out.println(pluginMapper.queryAll());
    }

    @Test
    public void test3(){
        System.out.println(pluginService.queryAll());
    }

    @Test
    public void test4(){
        System.out.println(pluginService.getPicByClazz("com.xziying.collectionAndForwarding"));

    }

    @Test
    public void test5(){
        System.out.println(Arrays.toString("1274206989441318".getBytes()));
    }

    @Test
    public void test6(){
        List<Verification> verifications = verificationService.queryByUid(1);
        for (Verification verification : verifications){
            System.out.println("插件：" + verification.getClazz() + " 所属QQ：" + verification.getFromQQ() + " 到期时间 : " + verification.getFormatEnd());
        }

        System.out.println(verificationService.authorize(1, "com.xziying.collectionAndForwarding", "all", 365));

        List<Verification> verifications2 = verificationService.queryByUid(1);
        for (Verification verification : verifications2){
            System.out.println("插件：" + verification.getClazz() + " 所属QQ：" + verification.getFromQQ() + " 到期时间 : " + verification.getFormatEnd());
        }
    }
}

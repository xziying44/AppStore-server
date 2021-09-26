package com.xziying.appstorecloud.test;

import com.xziying.appstorecloud.utils.REUtil;
import com.xziying.appstorecloud.utils.SMSUtil;
import org.junit.Test;

/**
 * Txy
 *
 * @author : xziying
 * @create : 2021-04-28 20:10
 */
public class Txy {
    @Test
    public void test1(){
        SMSUtil.sendCode("13389823737", "AKXZN", "10");
    }

    @Test
    public void test2(){
        System.out.println(REUtil.isMobile("24521251211"));
    }
}

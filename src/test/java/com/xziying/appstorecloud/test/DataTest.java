package com.xziying.appstorecloud.test;

import com.xziying.appstorecloud.dao.DataConfigTableMapper;
import com.xziying.appstorecloud.dao.DataUserConfigMapper;
import com.xziying.appstorecloud.domain.data.ConfigTable;
import com.xziying.appstorecloud.domain.data.UserConfig;
import com.xziying.appstorecloud.service.DataUserConfigService;
import org.apache.ibatis.binding.BindingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * DataTest
 *
 * @author : xziying
 * @create : 2021-04-14 15:30
 */
@RunWith(SpringRunner.class)
@MapperScan("com.xziying.appstorecloud.dao")
@SpringBootTest
public class DataTest {
    @Resource
    DataConfigTableMapper configTableMapper;

    @Resource
    DataUserConfigMapper userConfigMapper;

    @Resource
    DataUserConfigService userConfigService;

    @Test
    public void test1(){
        System.out.println(configTableMapper.add(new ConfigTable(1, "com.xziying.templateExample", "all")));
    }

    @Test
    public void test2(){
        List<ConfigTable> configTables = configTableMapper.queryByClazz(1, "com.xziying.templateExample");
        System.out.println(configTables);
    }

    @Test
    public void test3(){
        System.out.println(userConfigMapper.add(new UserConfig(2, "key2", "value2", true)));
    }

    @Test
    public void test4(){
        List<UserConfig> userConfigs = userConfigMapper.queryByDcid(2);
        System.out.println(userConfigs);
    }

    @Test
    public void test5(){
        UserConfig one = userConfigService.setKeyValue(4, 2, "one", "2222", false);
        System.out.println(one);
    }

    @Test
    public void test6(){
        System.out.println(userConfigService.setKeyValue(-1, 2, "one.ccccc", "ccccc", true));
    }
}

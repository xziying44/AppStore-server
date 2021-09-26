package com.xziying.appstorecloud.test;

import com.xziying.appstorecloud.dao.DataPluginConfigMapper;
import com.xziying.appstorecloud.domain.data.PluginConfig;
import com.xziying.appstorecloud.service.DataPluginConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * DataPluginConfigTest
 *
 * @author : xziying
 * @create : 2021-03-31 22:27
 */
@RunWith(SpringRunner.class)
@MapperScan("com.xziying.appstorecloud.dao")
@SpringBootTest
public class DataPluginConfigTest {
    @Resource
    DataPluginConfigMapper pluginConfigMapper;

    @Resource
    DataPluginConfigService dataPluginConfigService;

    @Test
    public void test1(){
        PluginConfig pluginConfig = new PluginConfig(1, 1, "all", "配置项", "配置值");
        pluginConfigMapper.add(pluginConfig);
    }

    @Test
    public void test2(){
        List<PluginConfig> all = pluginConfigMapper.queryConfig(1, 1, "all");
        System.out.println(all);
    }

    @Test
    public void test3(){
        Map<String, String> map = dataPluginConfigService.queryConfig(1, 1, "all");
        System.out.println(map);
    }

    @Test
    public void test4(){

    }
}

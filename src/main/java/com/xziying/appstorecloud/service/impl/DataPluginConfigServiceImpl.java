package com.xziying.appstorecloud.service.impl;

import com.xziying.appstorecloud.dao.DataPluginConfigMapper;
import com.xziying.appstorecloud.domain.data.PluginConfig;
import com.xziying.appstorecloud.service.DataPluginConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DataPluginConfigServiceImpl
 *
 * @author : xziying
 * @create : 2021-03-31 22:27
 */

@Service
public class DataPluginConfigServiceImpl implements DataPluginConfigService {

    @Resource
    DataPluginConfigMapper pluginConfigMapper;


    @Override
    public Map<String, String> queryConfig(int uid, int pid, String fromQQ) {
        List<PluginConfig> pluginConfigs = pluginConfigMapper.queryConfig(uid, pid, fromQQ);
        Map<String, String> reply = new HashMap<>();
        for (PluginConfig pluginConfig : pluginConfigs){
            reply.put(pluginConfig.getKey(), pluginConfig.getValue());
        }
        return reply;
    }


    @Override
    public int updateConfig(int uid, int pid, String fromQQ, String key, String value) {
        int status = pluginConfigMapper.updateByPid(new PluginConfig(uid, pid, fromQQ, key, value));
        if (status == 0){
            PluginConfig pluginConfig = new PluginConfig(uid, pid, fromQQ, key, value);
            int add = pluginConfigMapper.add(pluginConfig);
            if (add == 1)
                return 0;
            return -1;
        } else {
            return 0;
        }

    }
}

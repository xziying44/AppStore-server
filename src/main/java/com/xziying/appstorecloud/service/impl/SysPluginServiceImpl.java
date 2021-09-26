package com.xziying.appstorecloud.service.impl;

import com.xziying.appstorecloud.dao.SysPluginMapper;
import com.xziying.appstorecloud.domain.sys.Plugin;
import com.xziying.appstorecloud.service.SysPluginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * SysPluginServiceImpl
 *
 * @author : xziying
 * @create : 2021-04-03 20:20
 */
@Service
public class SysPluginServiceImpl implements SysPluginService {
    @Resource
    SysPluginMapper pluginMapper;

    @Override
    public List<Plugin> queryAll() {
        return pluginMapper.queryAll();
    }

    @Override
    public int getPicByClazz(String clazz) {
        return pluginMapper.getPicByClazz(clazz);
    }
}

package com.xziying.appstorecloud.service;

import com.xziying.appstorecloud.domain.sys.Plugin;

import java.util.List;

/**
 * SysPluginService
 *
 * @author : xziying
 * @create : 2021-04-03 20:20
 */
public interface SysPluginService {

    List<Plugin> queryAll();

    int getPicByClazz(String clazz);
}

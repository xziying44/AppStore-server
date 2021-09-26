package com.xziying.appstorecloud.service;

import java.util.Map;

/**
 * DataPluginConfigService
 *
 * @author : xziying
 * @create : 2021-03-31 22:27
 */
public interface DataPluginConfigService {

    /**
     * 根据用户ID和插件ID和所属处理QQ数据库取回key-value的Map映射表
     * @param uid 用户id
     * @param pid 插件id
     * @param fromQQ  所属QQ
     */
    Map<String, String> queryConfig(int uid, int pid, String fromQQ);


    /**
     * 更新插件配置，key不存在时创建一条记录
     * @param uid 用户id
     * @param pid 插件id
     * @param fromQQ 所属QQ
     * @param key 键
     * @param value 值
     * @return 0 为添加成功 其他为错误代码
     */
    int updateConfig(int uid, int pid, String fromQQ, String key, String value);
}

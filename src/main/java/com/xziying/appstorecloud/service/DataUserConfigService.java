package com.xziying.appstorecloud.service;

import com.xziying.appstorecloud.domain.data.ConfigTable;
import com.xziying.appstorecloud.domain.data.UserConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DataUserConfigService
 *
 * @author : xziying
 * @create : 2021-04-14 15:41
 */
public interface DataUserConfigService {

    /**
     * 创建虚拟数据库
     * @param uid 用户id
     * @param clazz 插件类名
     * @param fromQQ 欲服务的机器人Q
     */
    int createAVirtualDatabase(int uid, String clazz, String fromQQ);

    /**
     * 获取虚拟数据库
     * @param uid   用户id
     * @param clazz 插件类名
     */
    List<ConfigTable> getVirtualDatabase(int uid, String clazz);


    /**
     * 更新键值，不存在时创建
     * @param duid 记录id 小于0时为创建
     * @param key   键
     * @param value 值
     * @param noObj 不是对象
     * @return 更新后的对象
     */
    UserConfig setKeyValue(int duid, int dcid, String key, String value, Boolean noObj);

    /**
     * 删除键
     */
    int deleteKey(int duid);

    /**
     * 查询虚拟数据库
     * @param dcid 虚拟数据库id
     */
    List<UserConfig> queryVirtualDatabase(int dcid);
}

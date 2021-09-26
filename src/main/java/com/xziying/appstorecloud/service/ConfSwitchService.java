package com.xziying.appstorecloud.service;

import com.xziying.appstorecloud.domain.conf.ConfSwitch;

import java.util.List;

/**
 * ConfSwitchService
 *
 * @author : xziying
 * @create : 2021-04-09 20:41
 */
public interface ConfSwitchService {

    /**
     * 更新插件配置，配置不存在时创建一条记录
     * @param confSwitch 插件配置
     * @return 0 为添加成功 其他为错误代码
     */
    int update(ConfSwitch confSwitch);

    /**
     * 根据uid查询所有的插件配置
     * @param uid 用户id
     * @return 插件配置表
     */
    List<ConfSwitch> queryListByUid(int uid);

    /**
     * 交换权值配置
     */
    int swapSwitch(int uid, String clazz1, int weight1, String clazz2, int weight2);
}

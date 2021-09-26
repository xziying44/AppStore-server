package com.xziying.appstorecloud.service.impl;

import com.xziying.appstorecloud.dao.DataConfigTableMapper;
import com.xziying.appstorecloud.dao.DataUserConfigMapper;
import com.xziying.appstorecloud.domain.data.ConfigTable;
import com.xziying.appstorecloud.domain.data.UserConfig;
import com.xziying.appstorecloud.service.DataUserConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * DataUserConfigServiceImpl
 *
 * @author : xziying
 * @create : 2021-04-14 15:53
 */
@Service
public class DataUserConfigServiceImpl implements DataUserConfigService {

    @Resource
    DataConfigTableMapper configTableMapper;

    @Resource
    DataUserConfigMapper userConfigMapper;

    /**
     * 创建虚拟数据库
     *
     * @param uid    用户id
     * @param clazz  插件类名
     * @param fromQQ 欲服务的机器人Q
     */
    @Override
    public int createAVirtualDatabase(int uid, String clazz, String fromQQ) {
        return configTableMapper.add(new ConfigTable(uid, clazz, fromQQ));
    }

    /**
     * 获取虚拟数据库
     *
     * @param uid   用户id
     * @param clazz 插件类名
     */
    @Override
    public List<ConfigTable> getVirtualDatabase(int uid, String clazz) {
        return configTableMapper.queryByClazz(uid, clazz);
    }

    /**
     * 更新键值，不存在时创建
     *
     * @param duid  记录id 小于0时为创建
     * @param key   键
     * @param value 值
     * @param noObj 不是对象
     * @return 更新后的对象
     */
    @Override
    @Transactional
    public UserConfig setKeyValue(int duid, int dcid, String key, String value, Boolean noObj) {
        if (duid < 0){
            // 添加

            // 查询是否存在重复记录
            UserConfig exist = userConfigMapper.queryByKey(dcid, key);

            if (exist == null) {
                try {
                    // 查询是否存在可用记录
                    int l_duid = userConfigMapper.takeEmptyPosition();
                    UserConfig userConfig = new UserConfig(dcid, key, value, noObj);
                    userConfig.setDuid(l_duid);
                    if (userConfigMapper.update(userConfig) == 1) {
                        return userConfig;
                    }
                } catch (Throwable e) {
                    // 没有可用记录
                    if (userConfigMapper.add(new UserConfig(dcid, key, value, noObj)) == 1
                            && configTableMapper.counter(dcid, 1) == 1) {
                        UserConfig userConfig = userConfigMapper.queryByKey(dcid, key);
                        if (userConfig != null) {
                            return userConfig;
                        }
                    }
                }
            } else {
                // 修改
                exist.setValue(value);
                exist.setNoobj(noObj);
                if (userConfigMapper.update(exist) == 1){
                    return exist;
                }

            }
        } else {
            // 修改
            UserConfig userConfig = userConfigMapper.querySingle(duid);
            if (userConfig != null){
                userConfig.setValue(value);
                userConfig.setNoobj(noObj);
                if (userConfigMapper.update(userConfig) == 1){
                    return userConfig;
                }
            }
        }
        throw new Error("操作失败！");
    }

    /**
     * 删除键
     *
     */
    @Override
    public int deleteKey(int duid) {
        if (userConfigMapper.blanking(duid) == 1){
            return 0;
        }
        return -1;
    }

    /**
     * 查询虚拟数据库
     *
     * @param dcid 虚拟数据库id
     */
    @Override
    public List<UserConfig> queryVirtualDatabase(int dcid) {
        return userConfigMapper.queryByDcid(dcid);
    }
}

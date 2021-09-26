package com.xziying.appstorecloud.service.impl;

import com.xziying.appstorecloud.dao.ConfSwitchMapper;
import com.xziying.appstorecloud.domain.conf.ConfSwitch;
import com.xziying.appstorecloud.service.ConfSwitchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * ConfSwitchServiceImpl
 *
 * @author : xziying
 * @create : 2021-04-09 20:44
 */
@Service
public class ConfSwitchServiceImpl implements ConfSwitchService {

    @Resource
    ConfSwitchMapper switchMapper;

    /**
     * 更新插件配置，配置不存在时创建一条记录
     *
     * @param confSwitch 插件配置
     * @return 0 为添加成功 其他为错误代码
     */
    @Override
    public int update(ConfSwitch confSwitch) {
        int update = switchMapper.update(confSwitch);
        if (update == 0){
            // 记录不存在时
            if (switchMapper.add(confSwitch) == 1){
                return 0;
            }
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 根据uid查询所有的插件配置
     *
     * @param uid 用户id
     * @return 插件配置表
     */
    @Override
    public List<ConfSwitch> queryListByUid(int uid) {
        return switchMapper.queryListByUid(uid);
    }

    /**
     * 交换权值配置
     */
    @Override
    @Transactional
    public int swapSwitch(int uid, String clazz1, int weight1, String clazz2, int weight2) {
        if (switchMapper.updateWeight(uid, clazz1, weight1) != 1){
            throw new Error();
        }
        if (switchMapper.updateWeight(uid, clazz2, weight2) != 1){
            throw new Error();
        }
        return 0;
    }


}

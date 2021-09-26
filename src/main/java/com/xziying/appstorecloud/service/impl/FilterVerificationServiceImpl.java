package com.xziying.appstorecloud.service.impl;

import com.xziying.appstorecloud.dao.FilterVerificationMapper;
import com.xziying.appstorecloud.domain.filter.Verification;
import com.xziying.appstorecloud.service.FilterVerificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * FilterVerificationServiceImpl
 *
 * @author : xziying
 * @create : 2021-04-12 20:20
 */
@Service
public class FilterVerificationServiceImpl implements FilterVerificationService {

    @Resource
    FilterVerificationMapper verificationMapper;

    /**
     * 授权
     *
     * @param uid    用户id
     * @param clazz  插件
     * @param fromQQ 授权类型 all 或者 单QQ
     * @param day    天数
     * @return 成功0 失败 其他
     */
    @Override
    @Transactional
    public synchronized int authorize(int uid, String clazz, String fromQQ, int day) {
        Verification verification = verificationMapper.querySingle(uid, clazz, fromQQ);
        long currentTime = System.currentTimeMillis();
        long timestamp = (long)day * 24 * 60 * 60 * 1000;
        if (verification == null){
            // 创建授权
            verification = new Verification(uid, clazz, fromQQ, currentTime, currentTime + timestamp);
            if (verificationMapper.add(verification) == 1){
                return 0;
            }
        } else {
            // 在原授权进行新增
            if (verification.getEnd() < currentTime){
                // 已过期，从当前时间计算
                verification.setEnd(currentTime + timestamp);
            } else {
                // 未过期，从结束时间计算
                verification.setEnd(verification.getEnd() + timestamp);
            }
            if (verificationMapper.update(verification) == 1){
                return 0;
            }
        }
        return -1;
    }

    /**
     * 查询用户所有授权
     *
     * @param uid 用户id
     * @return 所有授权对象
     */
    @Override
    public synchronized List<Verification> queryByUid(int uid) {
        long currentTime = System.currentTimeMillis();
        List<Verification> verifications = verificationMapper.queryByUid(uid);
        // 过期删除
        verifications.removeIf(verification -> verification.getEnd() < currentTime);
        return verifications;
    }
}

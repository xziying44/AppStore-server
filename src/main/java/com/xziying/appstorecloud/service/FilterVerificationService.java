package com.xziying.appstorecloud.service;

import com.xziying.appstorecloud.domain.filter.Verification;

import java.util.List;

/**
 * FilterVerificationService
 *
 * @author : xziying
 * @create : 2021-04-12 20:20
 */
public interface FilterVerificationService {


    /**
     * 授权
     * @param uid 用户id
     * @param clazz 插件
     * @param fromQQ 授权类型 all 或者 单QQ
     * @param day 天数
     * @return 成功0 失败 其他
     */
    int authorize(int uid, String clazz, String fromQQ, int day);



    /**
     * 查询用户所有授权
     * @param uid 用户id
     * @return 所有授权对象
     */
    List<Verification> queryByUid(int uid);
}

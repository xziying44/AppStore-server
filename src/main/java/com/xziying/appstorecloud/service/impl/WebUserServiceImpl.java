package com.xziying.appstorecloud.service.impl;

import com.xziying.appstorecloud.dao.PayWalletMapper;
import com.xziying.appstorecloud.dao.SysUserMapper;
import com.xziying.appstorecloud.domain.sys.User;
import com.xziying.appstorecloud.service.WebUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * WebUserServiceImpl
 *
 * @author : xziying
 * @create : 2021-04-28 17:13
 */
@Service
public class WebUserServiceImpl implements WebUserService {

    @Resource
    SysUserMapper userMapper;

    @Resource
    PayWalletMapper walletMapper;

    /**
     * 登录
     */
    @Override
    public User login(String account, String password) {
        User user = userMapper.queryByAccount(account);
        if (user == null){
            // 通过手机号查找
            user = userMapper.queryByPhone(account);
        }
        if (user == null){
            // 通过邮箱查找
            user = userMapper.queryByEmail(account);
        }
        if (user == null){
            throw new Error("用户名不存在!");
        }
        if (!user.getPassword().equals(password)){
            throw new Error("密码错误!");
        }
        return user;
    }

    @Override
    public boolean doesThePhoneExist(String phone) {
        User user = userMapper.queryByPhone(phone);
        return user != null;
    }

    /**
     * 检测用户名是否被注册
     *
     * @param account 用户名
     * @return 被注册返回真 没注册返回假
     */
    @Override
    public boolean doesTheAccountExist(String account) {
        User user = userMapper.queryByAccount(account);
        return user != null;
    }

    /**
     * 注册
     */
    @Override
    @Transactional
    public synchronized int register(String phone, String account, String password) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setPhone(phone);
        user.setTime(System.currentTimeMillis());
        if (userMapper.add(user) != 1){
            throw new Error("创建用户失败，请联系管理员!");
        }
        user = userMapper.queryByAccount(account);
        if (walletMapper.create(user.getUid()) != 1){
            throw new Error("创建钱包失败，请联系管理员!");
        }
        return 0;
    }
}

package com.xziying.appstorecloud.service.impl;

import com.xziying.appstorecloud.abnormal.AccountDoesNotExistException;
import com.xziying.appstorecloud.abnormal.IncorrectAccountPasswordException;
import com.xziying.appstorecloud.constant.KeyField;
import com.xziying.appstorecloud.dao.SysUserMapper;
import com.xziying.appstorecloud.domain.sys.Token;
import com.xziying.appstorecloud.domain.sys.User;
import com.xziying.appstorecloud.redis.RedisToken;
import com.xziying.appstorecloud.service.SysUserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * SysUserService
 *
 * @author : xziying
 * @create : 2021-03-24 17:38
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    SysUserMapper userMapper;

    @Resource
    RedisToken redisToken;

    @Override
    public User login(String account, String password){
        User user = userMapper.queryByAccount(account);
        if (user == null){
            throw new AccountDoesNotExistException();
        }
        if (!user.getPassword().equals(password)){
            throw new IncorrectAccountPasswordException();
        }
        return user;
    }

    @Override
    public String getToken(User user) {
        Token token = new Token(user);
        redisToken.setToken(token);
        return token.getToken();
    }
}

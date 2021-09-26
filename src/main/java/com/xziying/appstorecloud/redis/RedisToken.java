package com.xziying.appstorecloud.redis;

import com.xziying.appstorecloud.domain.sys.Token;
import com.xziying.appstorecloud.domain.sys.User;
import com.xziying.appstorecloud.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RedisToken 令牌缓存池
 *
 * @author : xziying
 * @create : 2021-03-29 17:29
 */
@Component
public class RedisToken {
    @Resource
    RedisUtil redisUtil;

    /**
     * 写入token
     */
    public void setToken(Token token){
        redisUtil.set("token-" + token.getUid(), token.getToken(), 60 * 60 * 4); // 4小时过期
    }

    /**
     * 验证token
     */
    public boolean verificationToken(Token token){
        String str = (String) redisUtil.get("token-" + token.getUid());
        return str.equals(token.getToken());
    }
}

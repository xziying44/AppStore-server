package com.xziying.appstorecloud.domain.sys;

import com.xziying.appstorecloud.constant.KeyField;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Token
 *
 * @author : xziying
 * @create : 2021-03-29 17:52
 */
public class Token {
    Date issuedAt = new Date(); // 签证时间
    Date expiration;            // 过期时间
    int uid;                    // 用户id
    String token;               // token 令牌

    public Token(User user) {
        long now = System.currentTimeMillis();  //当前时间
        long exp = now + 1000 * 60 * 60 * 4;    //默认过期时间为4小时
        this.expiration = new Date(exp);
        this.uid = user.getUid();
        generate(); // 生成Token
    }

    public Token(User user, Date exp) {
        this.expiration = exp;
        this.uid = user.getUid();
        generate(); // 生成Token
    }

    public Token(String token) {
        this.token = token;
        resolve(); // 解析Token
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        resolve();
    }

    public int getUid() {
        return uid;
    }


    /**
     * 生成Token
     */
    private void generate(){
        JwtBuilder builder= Jwts.builder().setId(String.valueOf(uid))
                .setIssuedAt(this.issuedAt)        //用于设置签发时间
                .signWith(SignatureAlgorithm.HS256, KeyField.SIGNATURE)//用于设置签名秘钥
                .setExpiration(this.expiration);  //用于设置过期时间
        this.token = builder.compact();
    }

    /**
     * 解析Token
     */
    private void resolve(){
        Claims claims =
                Jwts.parser().setSigningKey(KeyField.SIGNATURE).parseClaimsJws(this.token).getBody();
        this.uid = Integer.parseInt(claims.getId());
        this.issuedAt = claims.getIssuedAt();
        this.expiration = claims.getExpiration();
    }
}

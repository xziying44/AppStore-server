package com.xziying.appstorecloud.test;

import com.xziying.appstorecloud.dao.SysUserMapper;
import com.xziying.appstorecloud.domain.sys.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * MyTest
 *
 * @author : xziying
 * @create : 2021-03-24 17:41
 */

public class MyTest {
    @Test
    public void test1(){
        long now = System.currentTimeMillis();//当前时间
        long exp = now + 1000*60;//过期时间为1分钟
        JwtBuilder builder= Jwts.builder().setId("1")
                .setIssuedAt(new Date())//用于设置签发时间
                .signWith(SignatureAlgorithm.HS256,"xziying")//用于设置签名秘钥
                .setExpiration(new Date(exp));//用于设置过期时间
        System.out.println( builder.compact() );
    }

    @Test
    public void test2(){
        String compactJws="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjE2OTM4MzA2LCJleHAiOjE2MTY5MzgzNjZ9.jfqKpkrxf1LnBhG5pXwq2L4okYK-06Lw9L3UKyKRhsM";
        Claims claims =
                Jwts.parser().setSigningKey("xziying").parseClaimsJws(compactJws).getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("签证时间:"+claims.getIssuedAt());
        System.out.println("过期时间:"+claims.getExpiration());
    }

    @Test
    public void test3(){
        System.out.println(("以前一直都认为有两个字节来记录长度（长度小也可以用一个字节记录），所以这个问题当时觉得就挺无聊的不过后来群里有人给了解释，突然才发现原来事情不是这么简单\n" +
                "\n" +
                "MYSQL COMPACT格式，每条记录有一个字节来表示NULL字段分布，如果表中有字段允许为空，则最大只能定到65532，如果没有字段允许为空，则那个字节可以节省，最大可以定义到65533，不知道是不是这个原因\n" +
                "\n" +
                "于是上网看了些资料，又在本地做了些实验，原来vachar的最大长度真的是不定的（根据是否有非空字段来决定）\n" +
                "在本地做了下实验，innodb+latin的环境").length());
    }
}

package com.xziying.appstorecloud.web.intercept;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * LoginBlockerConfig
 *
 * @author : xziying
 * @create : 2021-05-03 15:48
 */

@Configuration
public class LoginBlockerConfig implements WebMvcConfigurer {

    @Resource
    LoginBlocker loginBlocker;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginBlocker)
                .addPathPatterns("/home.html")
                .addPathPatterns("/pay");
    }
}

package com.xziying.appstorecloud.web.intercept;

import com.xziying.appstorecloud.constant.KeyField;
import com.xziying.appstorecloud.constant.SessionKey;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LoginBlocker 后台拦截器
 *
 * @author : xziying
 * @create : 2021-05-03 15:46
 */
@Component
public class LoginBlocker implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionKey.USER_INFO) == null){
            request.getRequestDispatcher("/error/nologin.html").forward(request, response);
            return false;
        }
        return true;
    }
}

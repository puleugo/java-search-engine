package com.megabrain.javasearchengine.global.interceptor;

import com.megabrain.javasearchengine.domain.user.dao.UserCookieManager;
import com.megabrain.javasearchengine.global.annotation.LoginGuards;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("loginInterceptor")
public class LoginInterceptor implements HandlerInterceptor {

    private final UserCookieManager userCookieManager;

    public LoginInterceptor(UserCookieManager userCookieManager) {
        this.userCookieManager = userCookieManager;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod // handler가 컨트롤러 메소드인지 확인
            && ((HandlerMethod) handler).hasMethodAnnotation(LoginGuards.class) // 컨트롤러 메소드에 @HasCorrectUserCookie가 붙어있는지 확인
        ) {
            if (!userCookieManager.hasValidateUserCookie(request.getCookies())) { // 쿠키가 유효한지 확인
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return true;
    }

}

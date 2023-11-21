package com.megabrain.javasearchengine.global.interceptor;

import com.megabrain.javasearchengine.domain.user.dao.UserDao;
import com.megabrain.javasearchengine.domain.user.dao.UserCookieManager;
import com.megabrain.javasearchengine.domain.user.model.UserRole;
import com.megabrain.javasearchengine.global.annotation.RequiredRole;
import org.apache.http.HttpException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component("roleInterceptor")
public class RoleInterceptor implements HandlerInterceptor {
    private final UserCookieManager userCookieManager;
    private final UserDao userDao;

    public RoleInterceptor(UserDao userDao, UserCookieManager userCookieManager) {
        this.userDao = userDao;
        this.userCookieManager = userCookieManager;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod &&
            ((HandlerMethod) handler).hasMethodAnnotation(RequiredRole.class)) {
            UserRole[] requiredRoles = ((HandlerMethod) handler).getMethodAnnotation(RequiredRole.class).value();
            if (!userCookieManager.hasValidateUserCookie(request.getCookies())) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            Optional<UserRole> userRole = userDao.findUserRoleByUsername(userCookieManager.getLoginUsername(request));

            if (userRole.isPresent() && !Arrays.asList(requiredRoles).contains(userRole.get())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }
        return true;
    }
}

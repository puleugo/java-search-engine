package com.megabrain.javasearchengine.global.resolver;

import com.megabrain.javasearchengine.domain.user.dao.UserCookieManager;
import com.megabrain.javasearchengine.global.annotation.LoginUsername;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component("loginUsernameResolver")
public class LoginUsernameResolver implements HandlerMethodArgumentResolver {

    private final UserCookieManager userCookieManager;

    LoginUsernameResolver(UserCookieManager userCookieManager) {
        this.userCookieManager = userCookieManager;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUsername.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return userCookieManager.getLoginUsername(request);
    }
}

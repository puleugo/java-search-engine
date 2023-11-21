package com.megabrain.javasearchengine.global.config;

import com.megabrain.javasearchengine.global.interceptor.LoginInterceptor;
import com.megabrain.javasearchengine.global.interceptor.RoleInterceptor;
import com.megabrain.javasearchengine.global.resolver.LoginUserResolver;
import com.megabrain.javasearchengine.global.resolver.LoginUsernameResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final RoleInterceptor roleInterceptor;
    private final LoginUsernameResolver loginUsernameResolver;
    private final LoginUserResolver loginUserResolver;

    public WebConfig(LoginInterceptor loginInterceptor, RoleInterceptor roleInterceptor, LoginUsernameResolver loginUsernameResolver, LoginUserResolver loginUserResolver) {
        this.loginInterceptor = loginInterceptor;
        this.roleInterceptor = roleInterceptor;
        this.loginUsernameResolver = loginUsernameResolver;
        this.loginUserResolver = loginUserResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor);
        registry.addInterceptor(roleInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUsernameResolver);
        resolvers.add(loginUserResolver);
    }
}

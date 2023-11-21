package com.megabrain.javasearchengine.global.annotation;

import com.megabrain.javasearchengine.domain.user.model.UserRole;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequiredRole {
    UserRole[] value();
}

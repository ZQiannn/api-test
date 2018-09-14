package com.ticktick.apiplatform.annotation.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * basic授权时的注解
 */
public @interface BasicAuth {

    //basic用户名
    String username();
    //basic密码
    String password();
}

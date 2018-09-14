package com.ticktick.apiplatform.annotation.base;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 声明为Api
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {

    //API的相对URL
    String url();

    //ContentType
    String contentType() default "application/json";

    //Accept
    String accept() default "application/json";

    //API名称
    String name();

    //请求方法
    RequestMethod method();
}

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

    String url();

    String contentType() default "application/json";

    String accept() default "application/json";

    String name();

    RequestMethod method();
}

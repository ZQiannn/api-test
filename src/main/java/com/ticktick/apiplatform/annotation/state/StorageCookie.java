package com.ticktick.apiplatform.annotation.state;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StorageCookie {

    //要保存的cookie名，不输默认保存全部
    String[] cookieNames() default {};

    //存在缓存中的key
    String storeKey();

}

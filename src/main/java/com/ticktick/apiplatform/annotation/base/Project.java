package com.ticktick.apiplatform.annotation.base;

import com.ticktick.apiplatform.constant.StringConstant;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 声明或指定Project
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Project {

    //Project名称
    String name() default StringConstant.DEFAULT;

    //基础Url例如域名
    String baseUrl() default "";
}

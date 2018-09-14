package com.ticktick.apiplatform.annotation.base;


import com.ticktick.apiplatform.constant.StringConstant;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定Group
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Group {

    String name() default StringConstant.DEFAULT;

}

package com.ticktick.apiplatform.annotation.base;


import com.ticktick.apiplatform.constant.PriorityLevel;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明为测试用例
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestCase {

    PriorityLevel level() default PriorityLevel.LOW;

    int order() default 100;

    boolean runOnce() default false;
}

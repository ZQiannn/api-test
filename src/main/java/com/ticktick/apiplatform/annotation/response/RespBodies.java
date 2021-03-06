package com.ticktick.apiplatform.annotation.response;

import com.ticktick.apiplatform.annotation.base.Property;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RespBodies {

    Property[] value() default {};

}

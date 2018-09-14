package com.ticktick.apiplatform.annotation.base;

public @interface Property {

    /**
     * path
     */
    String name();

    /**
     * el support, el format: $([a-z]|[A-Z]|_)+, eg: $t_id ,this value will be supplied by cache
     *
     * if annotate to response,it's just a demo value,please use matcher
     */
    String value() default "";

    /**
     * description
     */
    String desc() default "";

    /**
     * org.hamcrest.Matcher expression , eg: equalTo('a')
     *
     * el support, el format: $([a-z]|[A-Z]|_)+, eg: equalTo($t_id) ,this value will be supplied by
     * cache
     *
     * class parameter needs to type the full package, eg:isA(java.lang.String.class)
     */
    String matcher() default "";


    /**
     * if annotate to response,then the value will be stored in cache
     */
    String storeKey() default "";
}

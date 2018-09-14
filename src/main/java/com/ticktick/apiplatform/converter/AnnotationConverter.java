package com.ticktick.apiplatform.converter;

/**
 * @Title AnnotationConverter
 * @Description
 * @Author ZQian
 * @date: 2018/9/4 19:51
 */
public interface AnnotationConverter<S, T> {

    T convert(S s);

}

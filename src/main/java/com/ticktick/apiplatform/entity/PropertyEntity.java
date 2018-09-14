package com.ticktick.apiplatform.entity;

import lombok.Data;

/**
 * @Title DataEntity
 * @Description 键值对属性
 * @Author ZQian
 * @date: 2018/9/10 10:28
 */
@Data
public class PropertyEntity {

    /**
     * 键
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 描述
     */
    private String desc;

    /**
     * 匹配器
     */
    private String matcher;

    /**
     * 缓存时的key
     */
    private String storeKey;

}

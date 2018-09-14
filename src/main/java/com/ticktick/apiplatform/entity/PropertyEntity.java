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

    private String name;

    private String value;

    private String desc;

    private String matcher;

    private String storeKey;

}

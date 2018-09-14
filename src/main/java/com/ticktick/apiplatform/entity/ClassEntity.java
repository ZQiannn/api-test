package com.ticktick.apiplatform.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * @Title ClassEntity
 * @Description
 * @Author ZQian
 * @date: 2018/9/12 14:15
 */
@Data
@Table(name = "classes")
public class ClassEntity {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    private String md5;

}

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

    /**
     * 自增主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 类名
     */
    private String name;

    /**
     * 该类的md5编码
     */
    private String md5;

}

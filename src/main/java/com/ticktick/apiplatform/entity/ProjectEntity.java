package com.ticktick.apiplatform.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * @Title Project
 * @Description
 * @Author ZQian
 * @date: 2018/9/3 19:15
 */
@Data
@Table(name = "projects")
public class ProjectEntity {

    /**
     * 自增主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * Project名称
     */
    private String name;

    /**
     * 基础URL
     */
    private String baseUrl;

    /**
     * 属于用户
     */
    private Long userId;


}

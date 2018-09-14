package com.ticktick.apiplatform.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * @Title Group
 * @Description
 * @Author ZQian
 * @date: 2018/9/3 19:15
 */
@Data
@Table(name = "groups")
public class GroupEntity {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    private Long projectId;

}

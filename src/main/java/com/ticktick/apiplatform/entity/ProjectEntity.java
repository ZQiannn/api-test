package com.ticktick.apiplatform.entity;

import java.util.ArrayList;
import java.util.List;
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

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    private String baseUrl;

    private Long userId;


}

package com.ticktick.apiplatform.entity;

import io.restassured.http.Method;
import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * @Title TestEntity
 * @Description
 * @Author ZQian
 * @date: 2018/9/11 14:29
 */
@Data
@Table(name = "results")
public class ResultEntity {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Boolean success;

    private String responseBody;

    private String requestBody;

    @Column
    private Map<String, String> requestParams;

    @Column
    private Map<String, String> pathVariables;

    private Long time;

    private String failedMsg;

    private Long apiId;

    private String reportId;

    private Date start;

    private Method method;

    private String url;

    private Integer statusCode;

}

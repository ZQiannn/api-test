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

    /**
     * 自增主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 真实的返回数据
     */
    private String responseBody;

    /**
     * 真实的请求数据
     */
    private String requestBody;

    /**
     * 真实的form参数
     */
    @Column
    private Map<String, String> requestParams;

    /**
     * 真实的path参数
     */
    @Column
    private Map<String, String> pathVariables;

    /**
     * 请求时长
     */
    private Long time;

    /**
     * 失败的原因
     */
    private String failedMsg;

    /**
     * 关联的APIID
     */
    private Long apiId;

    /**
     * 报告ID
     */
    private String reportId;

    /**
     * 开始请求的时间
     */
    private Date start;

    /**
     * 请求方法
     */
    private Method method;

    /**
     * 真实请求url
     */
    private String url;

    /**
     * 真实http返回状态
     */
    private Integer statusCode;

}

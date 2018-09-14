package com.ticktick.apiplatform.entity;

import com.ticktick.apiplatform.constant.ApiSource;
import com.ticktick.apiplatform.constant.CookieStoreType;
import com.ticktick.apiplatform.constant.PriorityLevel;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * @Title Api
 * @Description
 * @Author ZQian
 * @date: 2018/9/4 14:03
 */
@Data
@Table(name = "apis")
public class ApiEntity {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    private String url;

    private Long projectId;

    private Long groupId;

    private Boolean isCase;

    private PriorityLevel priorityLevel;

    private String accept;

    private String contentType;

    @Column
    private List<PropertyEntity> requestBody;

    @Column
    private List<PropertyEntity> requestParams;

    @Column
    private List<PropertyEntity> pathVariables;

    private RequestMethod method;

    private String basicUsername;

    private String basicPassword;

    private CookieStoreType cookieType;

    @Column
    private List<String> cookieNames;

    private String storeCookieKey;

    private String withCookieKey;

    //Test Case部分

    private Integer testOrder;

    @Column
    private List<PropertyEntity> responseBody;

    @Column
    private List<Integer> statusIs;


    private Boolean deleted;

    private Boolean runOnce;

    private Long runCount;

    private ApiSource apiSource;

}

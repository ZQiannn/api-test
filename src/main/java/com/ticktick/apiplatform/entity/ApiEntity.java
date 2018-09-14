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

    /**
     * 自增主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 相对URL
     */
    private String url;

    /**
     * 关联的ProjectId
     */
    private Long projectId;
    /**
     * 关联的GroupId
     */
    private Long groupId;

    /**
     * 是否声明为测试用例
     */
    private Boolean isCase;

    /**
     * 优先级
     */
    private PriorityLevel priorityLevel;

    /**
     * accept属性
     */
    private String accept;

    /**
     * contentType属性
     */
    private String contentType;

    /**
     * requestBody配置
     */
    @Column
    private List<PropertyEntity> requestBody;

    /**
     * requestParam配置
     */
    @Column
    private List<PropertyEntity> requestParams;

    /**
     * pathVariables配置
     */
    @Column
    private List<PropertyEntity> pathVariables;

    /**
     * 请求方法
     */
    private RequestMethod method;

    /**
     * basic授权用户名
     */
    private String basicUsername;
    /**
     * basic授权密码
     */
    private String basicPassword;

    /**
     * cookie保存模式
     */
    private CookieStoreType cookieType;

    /**
     * cookie保存名称列表
     */
    @Column
    private List<String> cookieNames;

    /**
     * cookie保存的key
     */
    private String storeCookieKey;

    /**
     * 请求带上的cookieKey
     */
    private String withCookieKey;

    //Test Case部分
    /**
     * 测试顺序
     */
    private Integer testOrder;

    /**
     * 返回值匹配配置
     */
    @Column
    private List<PropertyEntity> responseBody;

    /**
     * 返回状态in
     */
    @Column
    private List<Integer> statusIs;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 是否只运行一次
     */
    private Boolean runOnce;

    /**
     * 运行的次数
     */
    private Long runCount;

    /**
     * 来源
     */
    private ApiSource apiSource;

}

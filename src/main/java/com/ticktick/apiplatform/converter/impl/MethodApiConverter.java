package com.ticktick.apiplatform.converter.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ticktick.apiplatform.annotation.auth.BasicAuth;
import com.ticktick.apiplatform.annotation.base.Api;
import com.ticktick.apiplatform.annotation.base.Deleted;
import com.ticktick.apiplatform.annotation.base.Property;
import com.ticktick.apiplatform.annotation.base.TestCase;
import com.ticktick.apiplatform.annotation.request.PathVars;
import com.ticktick.apiplatform.annotation.request.ReqBodies;
import com.ticktick.apiplatform.annotation.request.ReqParams;
import com.ticktick.apiplatform.annotation.response.RespBodies;
import com.ticktick.apiplatform.annotation.response.RespStatus;
import com.ticktick.apiplatform.annotation.state.StorageCookie;
import com.ticktick.apiplatform.annotation.state.WithCookie;
import com.ticktick.apiplatform.constant.ApiSource;
import com.ticktick.apiplatform.constant.CookieStoreType;
import com.ticktick.apiplatform.converter.AnnotationConverter;
import com.ticktick.apiplatform.entity.ApiEntity;
import com.ticktick.apiplatform.entity.PropertyEntity;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Title MethodApiConverter
 * @Description
 * @Author ZQian
 * @date: 2018/9/5 14:44
 */
@Component
public class MethodApiConverter implements AnnotationConverter<Method, ApiEntity> {

    @Override
    public ApiEntity convert(Method method) {
        ApiEntity apiEntity = new ApiEntity();
        //获取Api基本信息
        Api api = method.getAnnotation(Api.class);
        apiEntity.setName(api.name());
        apiEntity.setUrl(api.url());
        apiEntity.setAccept(api.accept());
        apiEntity.setContentType(api.contentType());
        apiEntity.setMethod(api.method());

        //获取请求参数  保存属性
        ReqParams reqParams = method.getAnnotation(ReqParams.class);
        if (reqParams != null) {
            Property[] values = reqParams.value();
            if (values.length > 0) {
                apiEntity.setRequestParams(getPropertyEntities(values));
            }
        }
        //获取path参数  保存属性
        PathVars pathVars = method.getAnnotation(PathVars.class);
        if (pathVars != null) {
            Property[] values = pathVars.value();
            if (values.length > 0) {
                apiEntity.setPathVariables(getPropertyEntities(values));
            }
        }
        //获取body参数  用String存?
        ReqBodies reqBodies = method.getAnnotation(ReqBodies.class);
        if (reqBodies != null) {
            Property[] values = reqBodies.body();
            if (values.length > 0) {
                apiEntity.setRequestBody(getPropertyEntities(values));
            }
            if (!StringUtils.isEmpty(reqBodies.value())) {
                //字符串解析成Entity
                JSONObject json = JSON.parseObject(reqBodies.value());
                List<PropertyEntity> propertyEntities = new ArrayList<>();
                json.forEach((k, v) -> {
                    PropertyEntity propertyEntity = new PropertyEntity();
                    propertyEntity.setName(k);
                    propertyEntity.setValue(String.valueOf(v));
                    propertyEntities.add(propertyEntity);
                });
                apiEntity.setRequestBody(propertyEntities);
            }
        }

        //Basic Auth
        BasicAuth basicAuth = method.getAnnotation(BasicAuth.class);
        if (basicAuth == null) {
            basicAuth = method.getClass().getAnnotation(BasicAuth.class);
        }
        if (basicAuth != null) {
            apiEntity.setBasicUsername(basicAuth.username());
            apiEntity.setBasicPassword(basicAuth.password());
        }

        TestCase testCase = method.getAnnotation(TestCase.class);
        if (testCase == null) {
            testCase = method.getClass().getAnnotation(TestCase.class);
        }
        if (testCase != null) {
            apiEntity.setIsCase(true);
            apiEntity.setPriorityLevel(testCase.level());
            apiEntity.setTestOrder(testCase.order());
            apiEntity.setRunOnce(testCase.runOnce());
        }

        //解析cookie
        StorageCookie cookieStorage = method.getAnnotation(StorageCookie.class);
        if (cookieStorage == null) {
            apiEntity.setCookieType(CookieStoreType.NONE);
        } else if (!StringUtils.isEmpty(cookieStorage.storeKey())) {
            String[] names = cookieStorage.cookieNames();
            if (names.length == 0) {
                apiEntity.setCookieType(CookieStoreType.ALL);
            } else {
                apiEntity.setCookieNames(Arrays.asList(names));
                apiEntity.setCookieType(CookieStoreType.PART);
            }
            apiEntity.setStoreCookieKey(cookieStorage.storeKey());
        }
        WithCookie withCookie = method.getAnnotation(WithCookie.class);
        if (withCookie != null && !StringUtils.isEmpty(withCookie.withKey())) {
            apiEntity.setWithCookieKey(withCookie.withKey());
        }

        //解析Resp
        RespBodies respBodies = method.getAnnotation(RespBodies.class);
        if (respBodies != null) {
            Property[] values = respBodies.value();
            if (values.length > 0) {
                apiEntity.setResponseBody(getPropertyEntities(values));
            }
        }

        //解析状态 is的优先级高于not
        RespStatus statusIs = method.getAnnotation(RespStatus.class);
        if (statusIs != null) {
            HttpStatus[] httpStatuses = statusIs.is();
            if (httpStatuses.length > 0) {
                apiEntity.setStatusIs(
                        Arrays.asList(httpStatuses).parallelStream().map(HttpStatus::value)
                                .collect(Collectors.toList()));
            }


        }

        //设置删除
        Deleted delete = method.getAnnotation(Deleted.class);
        if (delete != null) {
            apiEntity.setDeleted(true);
        } else {
            apiEntity.setDeleted(false);
        }

        apiEntity.setApiSource(ApiSource.CODE);

        return apiEntity;
    }

    private List<PropertyEntity> getPropertyEntities(Property[] values) {
        return Arrays.asList(values).parallelStream().map(v -> {
            PropertyEntity propertyEntity = new PropertyEntity();
            propertyEntity.setName(v.name());
            propertyEntity.setDesc(v.desc());
            propertyEntity.setValue(v.value());
            propertyEntity.setMatcher(v.matcher());
            propertyEntity.setStoreKey(v.storeKey());
            return propertyEntity;
        }).collect(Collectors.toList());
    }
}

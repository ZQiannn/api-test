package com.ticktick.apiplatform.service.impl;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

import com.ticktick.apiplatform.constant.CookieStoreType;
import com.ticktick.apiplatform.entity.ApiEntity;
import com.ticktick.apiplatform.entity.ProjectEntity;
import com.ticktick.apiplatform.service.ApiRunnerService;
import com.ticktick.apiplatform.service.ApiService;
import com.ticktick.apiplatform.service.ProjectService;
import com.ticktick.apiplatform.util.CacheUtil;
import com.ticktick.apiplatform.util.RequestUtil;
import io.restassured.http.Cookies;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/**
 * @Title ApiRunnerServiceImpl
 * @Description
 * @Author ZQian
 * @date: 2018/9/6 15:00
 */
@Service
public class ApiRunnerServiceImpl implements ApiRunnerService {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ProjectService projectService;

    @Override
    public Tuple2<Response, Map<String, Object>> runByApiId(Long apiId) {
        ApiEntity api = apiService.getById(apiId);
        Long projectId = api.getProjectId();
        ProjectEntity projectEntity = projectService.getById(projectId);
        return runByApi(api, projectEntity.getBaseUrl());
    }

    @Override
    public Tuple2<Response, Map<String, Object>> runByApi(ApiEntity apiEntity, String baseUrl) {

        //请求参数需要在下一步记录
        Map<String, Object> param = new HashMap<>(6);

        //设置请求头  TODO 设置请求连接池
        RequestSpecification request = given().config(config).accept(apiEntity.getAccept())
                .contentType(apiEntity.getContentType());

        //设置cookie
        if (!StringUtils.isEmpty(apiEntity.getWithCookieKey())) {
            Cookies cookies = CacheUtil.getCookies(apiEntity.getWithCookieKey());
            request = request.cookies(cookies);
            param.put("cookies", cookies);
        }

        //设置Basic密码
        if (!StringUtils.isEmpty(apiEntity.getBasicUsername())) {
            request = request.auth()
                    .basic(apiEntity.getBasicUsername(), apiEntity.getBasicPassword());
        }

        //设置请求参数
        if (!CollectionUtils.isEmpty(apiEntity.getRequestBody())) {
            //解析成JSON
            String requestBody = RequestUtil.getRequestBody(apiEntity.getRequestBody());
            request = request.body(requestBody);
            param.put("requestBody", requestBody);
        }
        if (!CollectionUtils.isEmpty(apiEntity.getRequestParams())) {
            Map<String, String> requestParam = RequestUtil
                    .getRequestParam(apiEntity.getRequestParams());
            request = request.params(requestParam);
            param.put("requestParams", requestParam);
        }
        if (!CollectionUtils.isEmpty(apiEntity.getPathVariables())) {
            Map<String, String> pathVariable = RequestUtil
                    .getPathVariable(apiEntity.getPathVariables());
            request = request.pathParams(pathVariable);
            param.put("pathVariables", pathVariable);
        }

        //设置url和请求
        Method method = Method.valueOf(apiEntity.getMethod().name());
        String url = baseUrl.concat(apiEntity.getUrl());
        Response response = request.when().request(method, url).thenReturn();
        param.put("method", method);
        param.put("url", url);

        //存储cookie
        if (!Objects.equals(apiEntity.getCookieType(), CookieStoreType.NONE)) {
            Cookies cookies;
            if (!CollectionUtils.isEmpty(apiEntity.getCookieNames())) {
                //指定了cookie name 提取出这部分
                cookies = new Cookies(apiEntity.getCookieNames().parallelStream().map(
                        response::detailedCookie).collect(Collectors.toList()));
            } else {
                //全部
                cookies = response.detailedCookies();
            }
            CacheUtil.storeCookies(apiEntity.getStoreCookieKey(), cookies);
        }

        //解析返回
        if (!CollectionUtils.isEmpty(apiEntity.getResponseBody())) {
            apiEntity.getResponseBody().parallelStream()
                    .filter(r -> !StringUtils.isEmpty(r.getStoreKey())).forEach(
                    r -> {
                        String storeKey = r.getStoreKey();
                        Object path = response.getBody().path(r.getName());
                        if (path != null) {
                            CacheUtil.setState(storeKey, path);
                        }

                    }
            );
        }

        return Tuples.of(response, param);
    }
}

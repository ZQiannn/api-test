package com.ticktick.apiplatform.service;

import com.ticktick.apiplatform.entity.ApiEntity;
import io.restassured.response.Response;
import java.util.Map;
import reactor.util.function.Tuple2;

/**
 * @Title ApiRunnerService
 * @Description 运行Api的Service
 * @Author ZQian
 * @date: 2018/9/6 14:52
 */
public interface ApiRunnerService {

    Tuple2<Response, Map<String, Object>> runByApiId(Long apiId);

    reactor.util.function.Tuple2<Response, Map<String, Object>> runByApi(ApiEntity apiEntity,String baseUrl);
}

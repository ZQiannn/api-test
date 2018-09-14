package com.ticktick.apiplatform.service;

import com.ticktick.apiplatform.entity.ApiEntity;
import io.restassured.response.Response;
import java.util.Map;

/**
 * @Title ApiTestService
 * @Description
 * @Author ZQian
 * @date: 2018/9/11 14:15
 */
public interface ApiTestService {

    void handleApiResult(Response response, ApiEntity entity,
            Map<String, Object> request, String reportId);

}

package com.ticktick.apiplatform.service.impl;

import com.ticktick.apiplatform.entity.ApiEntity;
import com.ticktick.apiplatform.entity.ResultEntity;
import com.ticktick.apiplatform.service.ApiTestService;
import com.ticktick.apiplatform.service.ResultService;
import com.ticktick.apiplatform.util.CacheUtil;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.MapContext;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @Title ApiTestServiceImpl
 * @Description
 * @Author ZQian
 * @date: 2018/9/11 14:15
 */
@Service
@Slf4j
public class ApiTestServiceImpl implements ApiTestService {

    private static final String MSG = "Condition %s match failed , expect: %s ,but %s .";

    private static final JexlEngine JEXL = new JexlBuilder().cache(512).strict(true).silent(false)
            .create();

    private static final Pattern PATTERN = Pattern.compile("\\(\\s*.+\\.class\\s*\\)");

    @Autowired
    private ResultService resultService;


    @Override
    public void handleApiResult(Response response, ApiEntity entity,
            Map<String, Object> request, String reportId) {

        //保存基本信息
        ResultEntity t = new ResultEntity();
        t.setApiId(entity.getId());
        t.setTime(response.getTime());
        t.setResponseBody(response.getBody().asString());
        t.setReportId(reportId);
        t.setStart(new Date());
        t.setRequestBody((String) request.get("requestBody"));
        t.setRequestParams((Map<String, String>) request.get("requestParams"));
        t.setPathVariables((Map<String, String>) request.get("pathVariables"));
        t.setUrl((String) request.get("url"));
        t.setMethod((Method) request.get("method"));
        t.setStatusCode(response.getStatusCode());

        //验证 HttpStatus
        List<Integer> statusIs = entity.getStatusIs();
        if (!CollectionUtils.isEmpty(statusIs)) {
            if (!statusIs.contains(response.getStatusCode())) {
                t.setFailedMsg(String.format(MSG, "response status is",
                        Arrays.toString(statusIs.toArray()),
                        response.getStatusCode()));
                t.setSuccess(false);
            }
        }

        //验证responsebody
        if (!CollectionUtils.isEmpty(entity.getResponseBody())) {
            entity.getResponseBody().parallelStream().forEach(r -> {
                ValidatableResponse then = response.then();

                String expression = r.getMatcher();
                //对EL表达式的处理
                Map<String, Object> stateMapping = CacheUtil.getStateMapping(expression);
                MapContext jexlContext = new MapContext(stateMapping);
                jexlContext.set("Util", new Matchers());

                //对 class的特殊处理
                java.util.regex.Matcher matcher = PATTERN.matcher(expression);
                if (matcher.find()) {
                    String group = matcher.group();
                    String className = group.replaceAll("[\\s,()]", "").replaceAll(".class", "");
                    try {
                        jexlContext.set("class", Class.forName(className));
                        expression = expression.replaceAll(group, "class");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                Matcher evaluate = (Matcher) JEXL.createExpression("Util.".concat(expression))
                        .evaluate(jexlContext);
                try {
                    then.body(r.getName(), evaluate);
                } catch (java.lang.AssertionError e) {
                    t.setFailedMsg(e.getMessage());
                    t.setSuccess(false);
                }

            });
        }

        if (t.getSuccess() == null) {
            t.setSuccess(true);
        }

        resultService.saveResult(t);
    }

}

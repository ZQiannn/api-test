package com.ticktick.api;

import com.alibaba.fastjson.JSON;
import com.ticktick.apiplatform.ApitestApplication;
import com.ticktick.apiplatform.service.ApiRunnerService;
import com.ticktick.apiplatform.util.CacheUtil;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Title RunApiTest
 * @Description
 * @Author ZQian
 * @date: 2018/9/6 17:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApitestApplication.class)
public class RunApiTest {

    @Autowired
    private ApiRunnerService apiRunnerService;

    @Test
    public void test() {
        Response result = apiRunnerService.runByApiId(1L).getT1();
        System.out.println(JSON.toJSONString(result));
        System.out.println(CacheUtil.getCookies("login"));
    }

}

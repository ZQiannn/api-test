package com.ticktick.apiplatform.runner;

import com.beust.jcommander.JCommander;
import com.ticktick.apiplatform.entity.ApiEntity;
import com.ticktick.apiplatform.entity.ResultEntity;
import com.ticktick.apiplatform.service.ApiRunnerService;
import com.ticktick.apiplatform.service.ApiService;
import com.ticktick.apiplatform.service.ApiTestService;
import com.ticktick.apiplatform.service.EmailService;
import com.ticktick.apiplatform.service.ProjectService;
import com.ticktick.apiplatform.service.ResultService;
import com.ticktick.apiplatform.support.CLISupport;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @Title ApiTestRunner
 * @Description
 * @Author ZQian
 * @date: 2018/9/10 16:10
 */
@Component
@Order(2)
@Slf4j
public class ApiTestRunner implements CommandLineRunner {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiRunnerService apiRunnerService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ApiTestService apiTestService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private EmailService emailService;

    @Override
    public void run(String... args) throws Exception {
        CLISupport cli = new CLISupport(apiService);
        JCommander jCommander = JCommander.newBuilder().addObject(cli).build();
        jCommander.parse(args);
        //解析CLI参数获得要测试的API
        List<ApiEntity> apis = cli.run(jCommander);
        if (apis==null){
            return;
        }

        String baseUrl = projectService.getBaseUrlById(apis.get(0).getProjectId());

        CountDownLatch latch = new CountDownLatch(apis.size());

        String reportId = UUID.randomUUID().toString();
        //已经排好序的Api做测试
        Flux<ApiEntity> flux = Flux.fromIterable(apis);
        flux.map(e -> apiRunnerService.runByApi(e, baseUrl)).zipWithIterable(apis)
                .subscribeOn(Schedulers.elastic())
                .subscribe(a -> {
                    try {
                        apiTestService.handleApiResult(a.getT1().getT1(), a.getT2(),a.getT1().getT2(), reportId);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    } finally {
                        latch.countDown();
                    }
                });

        latch.await();

        //发送测试报告
        List<ResultEntity> results = resultService.getByReportId(reportId);

        emailService.sendReports(results);
    }


}

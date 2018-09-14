package com.ticktick.apiplatform;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ticktick.apiplatform.scanner.ApiScannerConfigurer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@Import(ApiScannerConfigurer.class)
@MapperScan(basePackages = {"com.ticktick.apiplatform.mapper"})
public class ApitestApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication application = new SpringApplication(ApitestApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.setBannerMode(Mode.OFF);

        //去掉莫名其妙的日志
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger("org.springframework.core.env.StandardEnvironment").setLevel(Level.ERROR);

        //新建environment
        StandardEnvironment environment = new StandardEnvironment();

        //读取外部变量
        Properties properties = new Properties();

        String config = System.getProperty("config");
        File file = ResourceUtils.getFile(config);
        JSONObject json = JSON.parseObject(new FileInputStream(file), null);
        json2Properties("", properties, json);

        environment.getPropertySources().addLast(new PropertiesPropertySource("external-config",
                properties));
        application.setEnvironment(environment);
        application.run(args);
    }

    private static void json2Properties(String root, Properties properties, JSONObject json) {
        json.forEach((k, v) -> {
            if (v instanceof JSONObject) {
                json2Properties(k, properties, ((JSONObject) v));
            } else {
                if (StringUtils.isEmpty(root)) {
                    properties.setProperty(k, v.toString());
                } else {
                    properties.setProperty(root.concat(".").concat(k), v.toString());
                }
            }


        });
    }


}

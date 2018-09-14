package com.ticktick.apiplatform.scanner;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Title ApiScanner
 * @Description 扫描工程下的Api
 * @Author ZQian
 * @date: 2018/9/4 14:19
 */
public class ApiScannerConfigurer implements ImportBeanDefinitionRegistrar, ResourceLoaderAware,
        EnvironmentAware {

    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
            BeanDefinitionRegistry beanDefinitionRegistry) {

        ClassPathApiScanner scanner = new ClassPathApiScanner(beanDefinitionRegistry);
        if (this.resourceLoader != null) {
            scanner.setResourceLoader(this.resourceLoader);
        }
        scanner.registerFilters();
        scanner.doScan(environment.getProperty("api.scanner.base-package", "com.ticktick.apiplatform.api"));
    }
}

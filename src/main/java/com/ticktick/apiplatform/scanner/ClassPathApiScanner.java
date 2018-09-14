package com.ticktick.apiplatform.scanner;

import com.ticktick.apiplatform.util.CacheUtil;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.DigestUtils;

/**
 * @Title ApiScanner
 * @Description Api扫描器
 * @Author ZQian
 * @date: 2018/9/4 16:47
 */
public class ClassPathApiScanner extends ClassPathBeanDefinitionScanner {


    public ClassPathApiScanner(
            BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    /**
     * 注册扫描器的过滤
     */
    public void registerFilters() {
        this.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        this.addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        beanDefinitionHolders.parallelStream().forEach(holder -> {
            //获取定义的API接口
            try {
                FileSystemResource source = (FileSystemResource) holder.getBeanDefinition()
                        .getSource();
                String md5 = DigestUtils
                        .md5DigestAsHex(Objects.requireNonNull(source).getInputStream());
                CacheUtil.cacheApiName(holder.getBeanDefinition().getBeanClassName(), md5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return Collections.emptySet();
    }


    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isIndependent();
    }

    @Override
    protected void registerBeanDefinition(BeanDefinitionHolder definitionHolder,
            BeanDefinitionRegistry registry) {
        // ignore 扫描的接口不生成bean
    }
}

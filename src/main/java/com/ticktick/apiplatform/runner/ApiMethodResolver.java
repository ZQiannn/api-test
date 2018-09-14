package com.ticktick.apiplatform.runner;

import com.ticktick.apiplatform.annotation.base.Api;
import com.ticktick.apiplatform.annotation.base.Group;
import com.ticktick.apiplatform.annotation.base.Project;
import com.ticktick.apiplatform.constant.StringConstant;
import com.ticktick.apiplatform.converter.impl.GroupAnnotationConverter;
import com.ticktick.apiplatform.converter.impl.MethodApiConverter;
import com.ticktick.apiplatform.converter.impl.ProjectAnnotationConverter;
import com.ticktick.apiplatform.entity.ApiEntity;
import com.ticktick.apiplatform.entity.ClassEntity;
import com.ticktick.apiplatform.entity.GroupEntity;
import com.ticktick.apiplatform.entity.ProjectEntity;
import com.ticktick.apiplatform.service.ApiService;
import com.ticktick.apiplatform.service.ClassService;
import com.ticktick.apiplatform.service.GroupService;
import com.ticktick.apiplatform.service.ProjectService;
import com.ticktick.apiplatform.util.CacheUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Title ApiMethodResolver
 * @Description Api解析器
 * @Author ZQian
 * @date: 2018/9/5 13:34
 */
@Component
@Order(1)
public class ApiMethodResolver implements CommandLineRunner {

    @Autowired
    private ProjectAnnotationConverter projectAnnotationConverter;

    @Autowired
    private GroupAnnotationConverter groupAnnotationConverter;

    @Autowired
    private MethodApiConverter methodApiConverter;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private ClassService classService;


    @Override
    public void run(String... args) {
        ConcurrentHashMap<String, String> apiMd5s = CacheUtil.getApiMd5s();
        ArrayList<String> apiNames = new ArrayList<>();
        apiMd5s.forEach((name,md5)->{
            ClassEntity classEntity=new ClassEntity();
            classEntity.setName(name);
            classEntity.setMd5(md5);
            if (classService.upsertClass(classEntity)>0){
                apiNames.add(name);
            }
        });
        apiNames.parallelStream().forEach(className -> {
            try {
                Class<?> aClass = Class.forName(className);
                ProjectEntity projectEntity = projectAnnotationConverter
                        .convert(aClass.getAnnotation(Project.class));
                if (projectEntity == null) {
                    projectEntity = new ProjectEntity();
                    projectEntity.setName(StringConstant.DEFAULT);
                }
                //findAndSave projectEntity
                projectService.upsertProject(projectEntity);

                //解析Group
                GroupEntity groupEntity = groupAnnotationConverter
                        .convert(aClass.getAnnotation(Group.class));
                if (groupEntity != null) {
                    groupEntity.setProjectId(projectEntity.getId());
                    // findOrSave
                    groupService.upsertGroup(groupEntity);
                }

                //解析接口中的api方法
                Method[] methods = aClass.getMethods();
                ProjectEntity finalProjectEntity = projectEntity;
                Arrays.asList(methods).parallelStream()
                        .filter(m -> m.getAnnotation(Api.class) != null).forEach(m -> {
                    ApiEntity apiEntity = methodApiConverter.convert(m);

                    GroupEntity methodGroupEntity = groupAnnotationConverter
                            .convert(m.getAnnotation(Group.class));
                    if (methodGroupEntity != null) {
                        // findOrSave group
                        methodGroupEntity.setProjectId(finalProjectEntity.getId());
                        groupService.upsertGroup(methodGroupEntity);
                        apiEntity.setGroupId(methodGroupEntity.getId());
                    } else if (groupEntity != null) {
                        //
                        groupService.upsertGroup(groupEntity);
                        apiEntity.setGroupId(groupEntity.getId());
                    } else {
                        methodGroupEntity = new GroupEntity();
                        methodGroupEntity.setName(StringConstant.DEFAULT);
                        methodGroupEntity.setProjectId(finalProjectEntity.getId());
                        // find Or Save group
                        groupService.upsertGroup(methodGroupEntity);
                        apiEntity.setGroupId(methodGroupEntity.getId());
                    }
                    apiEntity.setProjectId(finalProjectEntity.getId());

                    apiService.upsertApi(apiEntity);
                });


            } catch (ClassNotFoundException e) {
                //ignore
            }
        });

    }

}

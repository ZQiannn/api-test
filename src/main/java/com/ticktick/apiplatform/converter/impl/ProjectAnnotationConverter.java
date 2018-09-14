package com.ticktick.apiplatform.converter.impl;

import com.ticktick.apiplatform.annotation.base.Project;
import com.ticktick.apiplatform.converter.AnnotationConverter;
import com.ticktick.apiplatform.entity.ProjectEntity;
import org.springframework.stereotype.Component;

/**
 * @Title ProjectAnnotationConverter
 * @Description
 * @Author ZQian
 * @date: 2018/9/5 11:37
 */
@Component
public class ProjectAnnotationConverter implements AnnotationConverter<Project, ProjectEntity> {

    @Override
    public ProjectEntity convert(Project project) {
        if (project == null) {
            return null;
        }
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(project.name());
        projectEntity.setBaseUrl(project.baseUrl());

        //默认为0号 Admin所有
        projectEntity.setUserId(0L);
        return projectEntity;
    }
}

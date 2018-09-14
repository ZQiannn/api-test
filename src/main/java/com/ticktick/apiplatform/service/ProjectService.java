package com.ticktick.apiplatform.service;

import com.ticktick.apiplatform.entity.ProjectEntity;

/**
 * @Title ProjectService
 * @Description
 * @Author ZQian
 * @date: 2018/9/5 16:20
 */
public interface ProjectService {

    void upsertProject(ProjectEntity projectEntity);

    ProjectEntity getById(Long projectId);

    String getBaseUrlById(Long projectId);
}

package com.ticktick.apiplatform.service.impl;

import com.ticktick.apiplatform.entity.ProjectEntity;
import com.ticktick.apiplatform.mapper.ProjectMapper;
import com.ticktick.apiplatform.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Builder;
import tk.mybatis.mapper.util.Sqls;

/**
 * @Title ProjectServiceImpl
 * @Description
 * @Author ZQian
 * @date: 2018/9/6 11:55
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public void upsertProject(ProjectEntity projectEntity) {
        Example example = new Builder(ProjectEntity.class).select("id")
                .where(Sqls.custom().andEqualTo("userId", projectEntity.getUserId())
                        .andEqualTo("name", projectEntity.getName())).build();
        ProjectEntity origin = projectMapper.selectOneByExample(example);
        if (origin == null) {
            projectMapper.insert(projectEntity);
        } else {
            projectEntity.setId(origin.getId());
            //不希望覆盖URL
            projectMapper.updateByPrimaryKeySelective(projectEntity);
        }
    }

    @Override
    public ProjectEntity getById(Long projectId) {
        return projectMapper.selectByPrimaryKey(projectId);
    }

    @Override
    public String getBaseUrlById(Long projectId) {
        Example example = new Builder(ProjectEntity.class).select("baseUrl")
                .where(Sqls.custom().andEqualTo("id", projectId)).build();

        return projectMapper.selectOneByExample(example).getBaseUrl();
    }
}

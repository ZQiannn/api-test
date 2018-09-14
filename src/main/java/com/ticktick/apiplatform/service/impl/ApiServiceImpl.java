package com.ticktick.apiplatform.service.impl;

import com.ticktick.apiplatform.entity.ApiEntity;
import com.ticktick.apiplatform.mapper.ApiMapper;
import com.ticktick.apiplatform.service.ApiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Builder;
import tk.mybatis.mapper.util.Sqls;

/**
 * @Title ApiServiceImpl
 * @Description
 * @Author ZQian
 * @date: 2018/9/6 11:39
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public int upsertApi(ApiEntity apiEntity) {
        //通过 projectId 和 name 确定一条api
        Example example = new Builder(ApiEntity.class).select("id")
                .where(Sqls.custom().andEqualTo("projectId", apiEntity.getProjectId())
                        .andEqualTo("groupId", apiEntity.getGroupId())
                        .andEqualTo("name", apiEntity.getName())).build();
        ApiEntity origin = apiMapper.selectOneByExample(example);
        if (origin == null) {
            return apiMapper.insert(apiEntity);
        } else {
            apiEntity.setId(origin.getId());
            return apiMapper.updateByPrimaryKey(apiEntity);
        }
    }

    @Override
    public ApiEntity getById(Long apiId) {
        return apiMapper.selectByPrimaryKey(apiId);
    }

    @Override
    public List<ApiEntity> getByParams(String projectName, String groupName, String apiName) {

        return apiMapper.selectByParam(projectName, groupName, apiName);
    }
}

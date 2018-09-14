package com.ticktick.apiplatform.service.impl;

import com.ticktick.apiplatform.entity.ApiEntity;
import com.ticktick.apiplatform.entity.GroupEntity;
import com.ticktick.apiplatform.mapper.GroupMapper;
import com.ticktick.apiplatform.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Builder;
import tk.mybatis.mapper.util.Sqls;

/**
 * @Title GroupServiceImpl
 * @Description
 * @Author ZQian
 * @date: 2018/9/6 11:39
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;


    @Override
    public void upsertGroup(GroupEntity groupEntity) {
        Example example = new Builder(GroupEntity.class).select("id")
                .where(Sqls.custom().andEqualTo("projectId", groupEntity.getProjectId())
                        .andEqualTo("name", groupEntity.getName())).build();

        GroupEntity origin = groupMapper.selectOneByExample(example);
        if (origin == null) {
            groupMapper.insert(groupEntity);
        } else {
            groupEntity.setId(origin.getId());
            groupMapper.updateByPrimaryKey(groupEntity);
        }
    }
}

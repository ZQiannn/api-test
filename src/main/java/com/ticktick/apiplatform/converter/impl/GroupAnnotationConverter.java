package com.ticktick.apiplatform.converter.impl;

import com.ticktick.apiplatform.annotation.base.Group;
import com.ticktick.apiplatform.converter.AnnotationConverter;
import com.ticktick.apiplatform.entity.GroupEntity;
import org.springframework.stereotype.Component;

/**
 * @Title ProjectAnnotationConverter
 * @Description
 * @Author ZQian
 * @date: 2018/9/5 11:37
 */
@Component
public class GroupAnnotationConverter implements AnnotationConverter<Group, GroupEntity> {

    @Override
    public GroupEntity convert(Group group) {
        if (group == null) {
            return null;
        }
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(group.name());
        return groupEntity;
    }
}

package com.ticktick.apiplatform.service;

import com.ticktick.apiplatform.entity.GroupEntity;

/**
 * @Title GroupService
 * @Description
 * @Author ZQian
 * @date: 2018/9/5 16:20
 */
public interface GroupService {

    void upsertGroup(GroupEntity groupEntity);
}

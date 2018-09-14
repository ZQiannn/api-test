package com.ticktick.apiplatform.service;

import com.ticktick.apiplatform.entity.ClassEntity;

/**
 * @Title ClassService
 * @Description
 * @Author ZQian
 * @date: 2018/9/12 14:17
 */
public interface ClassService {

    int upsertClass(ClassEntity classEntity);
}

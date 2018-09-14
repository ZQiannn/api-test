package com.ticktick.apiplatform.service;

import com.ticktick.apiplatform.entity.ApiEntity;
import java.util.List;

/**
 * @Title ApiService
 * @Description
 * @Author ZQian
 * @date: 2018/9/5 16:20
 */
public interface ApiService {

    /**
     * 新增或更新API
     * @param apiEntity
     * @return 返回新增的数量
     */
    int upsertApi(ApiEntity apiEntity);

    /**
     * 根据id查找API
     * @param apiId
     * @return
     */
    ApiEntity getById(Long apiId);

    /**
     * 根据参数获得API
     * @param projectName
     * @param groupName
     * @param apiName
     * @return
     */
    List<ApiEntity> getByParams(String projectName, String groupName, String apiName);
}

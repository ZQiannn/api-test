package com.ticktick.apiplatform.mapper;

import com.ticktick.apiplatform.entity.ApiEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Title ApiMapper
 * @Description
 * @Author ZQian
 * @date: 2018/9/6 11:40
 */
public interface ApiMapper extends Mapper<ApiEntity> {

    List<ApiEntity> selectByParam(@Param("projectName") String projectName,
            @Param("groupName") String groupName,
            @Param("apiName") String apiName);
}

package com.ticktick.apiplatform.service;

import com.ticktick.apiplatform.entity.ResultEntity;
import java.util.List;

/**
 * @Title ResultService
 * @Description
 * @Author ZQian
 * @date: 2018/9/12 14:46
 */
public interface ResultService {

    /**
     * 保存结果
     */
    int saveResult(ResultEntity t);

    List<ResultEntity> getByReportId(String reportId);
}

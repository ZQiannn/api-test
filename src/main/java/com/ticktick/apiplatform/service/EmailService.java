package com.ticktick.apiplatform.service;

import com.ticktick.apiplatform.entity.ResultEntity;
import java.util.List;

/**
 * @Title EmailService
 * @Description
 * @Author ZQian
 * @date: 2018/9/12 14:46
 */
public interface EmailService {

    void sendReports(List<ResultEntity> results);
}

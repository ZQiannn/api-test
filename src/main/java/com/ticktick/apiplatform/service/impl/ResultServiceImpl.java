package com.ticktick.apiplatform.service.impl;

import com.ticktick.apiplatform.entity.ResultEntity;
import com.ticktick.apiplatform.mapper.ResultMapper;
import com.ticktick.apiplatform.service.ResultService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

/**
 * @Title ResultService
 * @Description
 * @Author ZQian
 * @date: 2018/9/12 14:46
 */
@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultMapper resultMapper;

    @Override
    public int saveResult(ResultEntity t) {
        return resultMapper.insert(t);
    }

    @Override
    public List<ResultEntity> getByReportId(String reportId) {
        Example example = Example.builder(ResultEntity.class)
                .where(Sqls.custom().andEqualTo("reportId", reportId)).build();

        return resultMapper.selectByExample(example);
    }


}

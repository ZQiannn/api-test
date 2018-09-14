package com.ticktick.apiplatform.service.impl;

import com.ticktick.apiplatform.entity.ClassEntity;
import com.ticktick.apiplatform.mapper.ClassMapper;
import com.ticktick.apiplatform.service.ClassService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Builder;
import tk.mybatis.mapper.util.Sqls;

/**
 * @Title ClassServiceImpl
 * @Description
 * @Author ZQian
 * @date: 2018/9/12 14:19
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public int upsertClass(ClassEntity classEntity) {
        Example example = new Builder(ClassEntity.class).select("id","md5")
                .where(Sqls.custom()
                        .andEqualTo("name", classEntity.getName())).build();
        ClassEntity origin = classMapper.selectOneByExample(example);
        if (origin == null) {
            return classMapper.insert(classEntity);
        } else {
            if (!Objects.equals(classEntity.getMd5(),origin.getMd5())){
                classEntity.setId(origin.getId());
                return classMapper.updateByPrimaryKey(classEntity);
            }

        }
        return 0;
    }
}

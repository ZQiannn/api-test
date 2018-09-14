package com.ticktick.apiplatform.mapper.handler;

import com.alibaba.fastjson.JSON;
import com.ticktick.apiplatform.entity.PropertyEntity;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title StringListTypeHandler
 * @Description 对List<String>的mabatis的字段映射
 * @Author ZQian
 * @date: 2017/8/8 下午6:14
 */
@MappedTypes({List.class})
public class ListTypeHandler extends BaseTypeHandler<List<?>> {

    private static Logger LOG = LoggerFactory.getLogger(ListTypeHandler.class);


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<?> parameter,
            JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public List<?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.getStringList(rs.getString(columnName));
    }

    @Override
    public List<?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.getStringList(rs.getString(columnIndex));
    }

    @Override
    public List<?> getNullableResult(CallableStatement rs, int columnIndex)
            throws SQLException {
        return this.getStringList(rs.getString(columnIndex));
    }

    private List<?> getStringList(String value) {
        if (value == null) {
            return Collections.emptyList();
        }
        try {
            return JSON.parseArray(value, PropertyEntity.class);
        } catch (Exception e) {
            try {
                return JSON.parseArray(value, Integer.class);
            } catch (Exception e1) {
                return JSON.parseArray(value, String.class);
            }
        }
    }
}

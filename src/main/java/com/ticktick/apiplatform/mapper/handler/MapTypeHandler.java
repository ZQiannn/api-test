package com.ticktick.apiplatform.mapper.handler;

import com.alibaba.fastjson.JSON;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * @Title MybatisHashMapTypeHandler
 * @Description
 * @Author ZQian
 * @date: 2018/9/6 12:00
 */
@MappedTypes(Map.class)
public class MapTypeHandler extends BaseTypeHandler<Map> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map map,
            JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(map));
    }

    @Override
    public Map getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JSON.parseObject(rs.getString(columnName));
    }

    @Override
    public Map getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JSON.parseObject(rs.getString(columnIndex));
    }

    @Override
    public Map getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return JSON.parseObject(cs.getString(columnIndex));
    }
}

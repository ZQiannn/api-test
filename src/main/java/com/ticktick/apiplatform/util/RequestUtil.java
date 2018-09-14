package com.ticktick.apiplatform.util;

import com.ticktick.apiplatform.entity.PropertyEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

/**
 * @Title RequestBodyUtil
 * @Description
 * @Author ZQian
 * @date: 2018/9/10 14:54
 */
public class RequestUtil {

    public static String getRequestBody(List<PropertyEntity> propertyEntities) {
        PropertiesToJsonConverter converter = new PropertiesToJsonConverter();
        Map<String, String> map = convertProperty2Map(propertyEntities);
        return converter.parseToJson(map);
    }

    public static Map<String, String> getRequestParam(List<PropertyEntity> requestParams) {
        return convertProperty2Map(requestParams);
    }

    public static Map<String, String> getPathVariable(List<PropertyEntity> pathVariable) {
        return convertProperty2Map(pathVariable);
    }


    private static Map<String, String> convertProperty2Map(
            List<PropertyEntity> entities) {
        Map<String, String> map = new HashMap<>(entities.size());
        entities.parallelStream().forEach(e -> {
            //解析el标记
            map.put(e.getName(),CacheUtil.getStateValue(e.getValue()));

        });
        return map;
    }
}

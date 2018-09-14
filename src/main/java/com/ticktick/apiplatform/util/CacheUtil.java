package com.ticktick.apiplatform.util;

import io.restassured.http.Cookies;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title CacheUtil
 * @Description
 * @Author ZQian
 * @date: 2018/9/5 11:54
 */
public class CacheUtil {

    private static final Pattern EL = Pattern.compile("\\$([a-z]|[A-Z]|_)+");

    private static final ConcurrentHashMap<String, String> API_CLASS = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, Cookies> COOKIES = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, Object> STATE = new ConcurrentHashMap<>();

    public static void cacheApiName(String className, String md5) {
        API_CLASS.put(className, md5);
    }

    public static ConcurrentHashMap<String, String> getApiMd5s() {
        return API_CLASS;
    }


    public static void storeCookies(String key, Cookies cookies) {
        COOKIES.put(key, cookies);
    }

    public static Cookies getCookies(String key) {
        return COOKIES.get(key);
    }

    public static Object getState(String key) {

        return STATE.get(key);
    }

    public static void setState(String key, Object state) {
        STATE.put(key, state);
    }

    public static String getStateValue(String value) {
        if (!value.contains("$")) {
            return value;
        } else {
            //提取value中的el表达式
            Matcher matcher = EL.matcher(value);
            while (matcher.find()) {
                String group = matcher.group();
                Object state = getState(group.replace("$", ""));
                if (state != null) {
                    value = value.replace(group, String.valueOf(state));
                }

            }
            return value;
        }

    }

    public static Map<String, Object> getStateMapping(String value) {
        Map<String, Object> map = new HashMap<>();
        if (!value.contains("$")) {
            map.put(value, value);
            return map;
        } else {
            //提取value中的el表达式
            Matcher matcher = EL.matcher(value);
            while (matcher.find()) {
                String group = matcher.group();
                Object state = getState(group.replace("$", ""));
                if (state != null) {
                    map.put(group, state);
                } else {
                    map.put(group, group);
                }

            }
            return map;
        }
    }



}

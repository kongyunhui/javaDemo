package com.kyh.jsonpath;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.internal.JsonContext;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;

/**
 * 对JSON-Path做一层简单封装：</br>
 * 1. 使用Jackson作为JSON引擎,以便支持从JSON转换成对象</br>
 * 2. 限制可用的API，简化使用</br>
 * 3. 做一些增强</br>
 * 参考：
 * http://www.baeldung.com/guide-to-jayway-jsonpath
 * https://github.com/jayway/JsonPath
 *
 * @author li.peilong
 */
public class JsonPathUtil {
    // 使用Jackson
    static {
        Configuration.setDefaults(new Configuration.Defaults() {

            private final JsonProvider jsonProvider = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                EnumSet<Option> options = EnumSet.noneOf(Option.class);
                // 路径不存在不抛异常，返回null
                options.add(Option.DEFAULT_PATH_LEAF_TO_NULL);
                return options;
            }
        });
    }

    /**
     * 查找指定路径的数据
     *
     * @param json     JSON数据
     * @param jsonPath 要查找的路径
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T find(String json, String jsonPath) throws Exception {
        try {
            T data = JsonPath.read(json, jsonPath);
            return convert(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static <T> T convert(T data) {
        if (data instanceof List) {
            List result = new ArrayList();
            for (Object obj : (List) data) {
                if (obj != null) {
                    result.add(obj);
                }
            }
            return (T) result;
        }
        return data;
    }

    public static <T> T find(Object json, String jsonPath) throws Exception {
        if (json == null) {
            return null;
        }
        return find(JSON.toJSONString(json), jsonPath);
    }

    /**
     * 查找满足条件的第一条数据
     *
     * @param json     JSON数据
     * @param jsonPath 要查找的路径
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T findOne(String json, String jsonPath) throws Exception {
        T result = find(json, jsonPath);
        if (result instanceof List) {
            if (((List) result).isEmpty() == false) {
                return (T) ((List) result).get(0);
            } else {
                return null;
            }
        }
        return result;
    }

    public static <T> T findOne(Object json, String jsonPath) throws Exception {
        if (json == null) {
            return null;
        }
        return findOne(JSON.toJSONString(json), jsonPath);
    }

    /**
     * 查找满足条件的第一条数据,支持设置默认值
     *
     * @param json
     * @param jsonPath
     * @param defaultVal
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T findOne(String json, String jsonPath, T defaultVal) throws Exception {
        T result = find(json, jsonPath);
        if (result instanceof List) {
            if (((List) result).size() > 0) {
                return (T) ((List) result).get(0);
            } else {
                return defaultVal;
            }
        }
        if (result == null) {
            return defaultVal;
        }
        return result;
    }

    /**
     * 查询指定路径整数
     *
     * @param json
     * @param jsonPath
     * @return
     * @throws Exception
     */
    public static Integer findInteger(String json, String jsonPath) throws Exception {
        Object result = findOne(json, jsonPath);
        if (result == null) {
            return null;
        }
        return Integer.parseInt(result.toString());
    }

    /**
     * 查询指定路径整数，支持默认值
     *
     * @param json
     * @param jsonPath
     * @param defaultValue
     * @return
     * @throws Exception
     */
    public static Integer findInteger(String json, String jsonPath, int defaultValue) throws Exception {
        Object result = findOne(json, jsonPath);
        if (result == null) {
            return defaultValue;
        }
        return Integer.parseInt(result.toString());
    }

    /**
     * 将搜索到的数据转换成指定的对象
     *
     * @param json
     * @param jsonPath
     * @param type
     */
    public static <T> T find(String json, String jsonPath, Class<T> type) throws Exception {
        try {
            return new JsonContext().parse(json).read(jsonPath, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查找枚举值
     *
     * @param json
     * @param jsonPath
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends Enum<T>> T findEnum(String json, String jsonPath, Class<T> type) throws Exception {
        try {
            String data = findOne(json, jsonPath);
            return Enum.valueOf(type, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T find(Object json, String jsonPath, Class<T> type) throws Exception {
        if (json == null) {
            return null;
        }
        return find(JSON.toJSONString(json), jsonPath, type);
    }
}

package com.kuafu.web.dynamic;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.kuafu.common.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoConverter {

    private static final String VO_PACKAGE = "com.kuafu.web.vo";
    private static final String ENTITY_PACKAGE = "com.kuafu.web.entity";

    public static Object convert(String tableName, Map<String, Object> data) throws Exception {
        String className = VO_PACKAGE + "." + toClassName(tableName) + "VO";
        Class<?> voClass = Class.forName(className);

        Map<String, Object> camelMap = toCamelCaseMapWithVo(data, voClass);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 显式忽略未知字段
        return mapper.convertValue(camelMap, voClass);
    }

    public static Object convertPageVo(String tableName, Map<String, Object> data) throws Exception {
        String className = VO_PACKAGE + "." + toClassName(tableName) + "PageVO";
        Class<?> voClass = Class.forName(className);

        Map<String, Object> camelMap = toCamelCaseMapWithVo(data, voClass);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 显式忽略未知字段

        return mapper.convertValue(camelMap, voClass);
    }

    private static String toClassName(String tableName) {

        String entityName = tableName2Entity(tableName);

        return StringUtils.dbStrToHumpUpper(entityName);
    }

    private static Map<String, Object> toCamelCaseMapWithVo(Map<String, Object> original, Class<?> clazz) {

        Map<String, String> fieldNameMap = voFiledNameMap(clazz);
        Map<String, Class<?>> fieldTypeMap = voFiledTypeMap(clazz);

        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : original.entrySet()) {
            String key = StringUtils.lowerCase(StringUtils.dbStrToHumpLower(entry.getKey()));
            Object value = entry.getValue();

            String defaultValue = entry.getKey();
            if (defaultValue.contains("_")) {
                defaultValue = StringUtils.dbStrToHumpLower(entry.getKey());
            }
            String fieldKey = fieldNameMap.getOrDefault(key, defaultValue);

            //字段目标类型
            Class<?> targetType = fieldTypeMap.getOrDefault(fieldKey, Object.class);
            if (value != null
                    && !Collection.class.isAssignableFrom(targetType)
                    && !targetType.isArray()
                    && (value instanceof List)) {
                List<?> listValue = ((List<?>) value);
                if (!listValue.isEmpty()) {
                    value = listValue.get(0);
                }
            } else if (value != null
                    && (Collection.class.isAssignableFrom(targetType) || targetType.isArray())
                    && !(value instanceof List)) {
                value = Lists.newArrayList(value);
            }

            result.put(fieldKey, value);
        }
        return result;
    }

    /**
     * 表名处理，去掉 t_ 开头
     *
     * @param tableName table name
     * @return table name
     */
    public static String tableName2Entity(String tableName) {
        return tableName.startsWith("t_") ? tableName.substring("t_".length()) : tableName;
    }


    public static boolean checkTableAllExist(String tableName) {
        String className = ENTITY_PACKAGE + "." + toClassName(tableName) + "All";
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Map<String, String> voFiledNameMap(Class<?> clazz) {
        Map<String, String> fieldMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            fieldMap.put(StringUtils.lowerCase(field.getName()), field.getName());
        }
        return fieldMap;
    }

    public static Map<String, Class<?>> voFiledTypeMap(Class<?> clazz) {
        Map<String, Class<?>> fieldMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            fieldMap.put(field.getName(), field.getType());
        }
        return fieldMap;
    }
}

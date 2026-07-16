package com.kuafu.common.util;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.*;
import com.kuafu.common.component.RequestDataContext;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@UtilityClass
public class WrapperFactory {

    public static <T> QueryWrapper<T> createQueryWrapper(Class<T> entityType) {
        return new QueryWrapper<>();
    }

    public static <T> RequestDataContext bean2RequestDataByGson(T bean) {
        RequestDataContext request = new RequestDataContext();
        Gson gson = new GsonBuilder().create();
        JsonObject jopublic = gson.toJsonTree(bean).getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jopublic.entrySet()) {
            if (entry.getValue().isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = entry.getValue().getAsJsonPrimitive();
                try {
                    Field f = JsonPrimitive.class.getDeclaredField("value");
                    f.setAccessible(true);

                    Object obj = f.get(jsonPrimitive);
                    request.setData(entry.getKey(), obj);

                } catch (NoSuchFieldException e) {
                } catch (IllegalAccessException e) {
                }
            }
        }
        return request;
    }

    public static <T> RequestDataContext bean2RequestData(T bean) {
        RequestDataContext request = new RequestDataContext();
        Class<?> clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue;
            try {
                fieldValue = field.get(bean);
                if (fieldValue != null) {
                    request.setData(fieldName, fieldValue);
                }
            } catch (IllegalAccessException e) {
                // Handle exception if needed
            }
        }
        return request;
    }

    public static Object getTableModelId(Object obj) {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                Object fieldValue;
                try {
                    fieldValue = field.get(obj);
                    if (fieldValue != null) {
                        return fieldValue;
                    }
                } catch (IllegalAccessException e) {
                    // Handle exception if needed
                }
            }
        }
        return null;
    }

    public static Object getPassword(Object obj, String key) {
        List<String> search;
        if (StringUtils.isEmpty(key)) {
            search = Arrays.asList("password", "passwd");
        } else {
            search = Arrays.asList(key);
        }
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (search.contains(fieldName)) {
                Object fieldValue;
                try {
                    fieldValue = field.get(obj);
                    if (fieldValue != null) {
                        return fieldValue;
                    }
                } catch (IllegalAccessException e) {
                    // Handle exception if needed
                }
            }
        }
        return null;
    }

}

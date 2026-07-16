package com.kuafu.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JSON {

    private static JSON instance = new JSON();
    private Gson gson;

    private JSON() {
        this.gson = new GsonBuilder().create();
    }

    public static String toJSONString(Object obj) {
        return instance.toJson(obj);
    }

    public static <T> T parseObject(String value, Class<T> clazz) {
        return instance.fromJson(value, clazz);
    }

    public static <T> List<T> parseArray(JsonElement element) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        return instance.fromJson(element, type);
    }

    public String toJson(Object obj) {
        return this.gson.toJson(obj);
    }

    public <T> T fromJson(String value, Class<T> clazz) {
        return this.gson.fromJson(value, clazz);
    }

    public <T> T fromJson(JsonElement element, Type typeOf) {
        return this.gson.fromJson(element, typeOf);
    }


}

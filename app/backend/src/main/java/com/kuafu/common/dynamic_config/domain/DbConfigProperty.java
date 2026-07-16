package com.kuafu.common.dynamic_config.domain;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Data
public class DbConfigProperty {

    /**
     * 属性名
     */
    private String propertyName;


    /**
     * 属性对象
     */
    private Object propertyClass;


    /**
     * 属性set方法
     */
    private Field propertyField;
}

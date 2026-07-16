package com.kuafu.common.dynamic_config.config;

import com.kuafu.common.dynamic_config.annoation.DBConfiguration;
import com.kuafu.common.dynamic_config.domain.DbConfigProperty;
import com.kuafu.common.dynamic_config.event.DbConfigurationEvent;
import com.kuafu.common.dynamic_config.service.DynamicRefreshService;
import com.kuafu.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DynamicConfigCenterConfigHadnler implements ApplicationRunner, ApplicationListener<DbConfigurationEvent> {

    @Resource
    private ApplicationContext applicationContext;


    private static Map<String, List<DbConfigProperty>> dbConfigPropertyMap = new ConcurrentHashMap<>();


    @Resource
    private DynamicRefreshScope dynamicRefreshScope;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(DBConfiguration.class);
        Collection<Object> allConfigurationObjList = beansWithAnnotation.values();
////      获取所有被标记了@Configuration注解的类
//        String[] beanNames = applicationContext.getBeanDefinitionNames();
//        List<Object> allConfigurationObjList = Arrays.stream(beanNames)
//                .map(beanName -> applicationContext.getBean(beanName))
//                .filter(o -> AopUtils.getTargetClass(o).equals(TestConfig.class))
//                .collect(Collectors.toList());
//      维护一个对象，属性 和 类以及对应 set 方法的映射关系

        for (Object o : allConfigurationObjList) {

            Class<?> actualClass = AopProxyUtils.ultimateTargetClass(o);

// 如果还是代理类，再取父类
            if (actualClass.getName().contains("$$")) {
                actualClass = actualClass.getSuperclass();
            }

            Field[] declaredFields = actualClass.getDeclaredFields();
            Class<?> originClass = o.getClass();


            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(Value.class)) {
//                  解析属性的值
                    Value annotation = field.getAnnotation(Value.class);
                    String value = annotation.value();
                    if (StringUtils.isEmpty(value)) {
                        continue;
                    }
                    //                  获取真实的属性值
                    value = value.replace("${", "").replace("}", "");
                    if (value.contains(":")) {
                        String[] split = value.split(":");
                        value = split[0];
                    }
                    DbConfigProperty dbConfigProperty = new DbConfigProperty();
                    dbConfigProperty.setPropertyName(value);
                    dbConfigProperty.setPropertyClass(o);
                    dbConfigProperty.setPropertyField(field);

                    if (dbConfigPropertyMap.containsKey(value)) {
                        dbConfigPropertyMap.get(value).add(dbConfigProperty);
                    } else {
                        final ArrayList<DbConfigProperty> value1 = new ArrayList<>();
                        value1.add(dbConfigProperty);
                        dbConfigPropertyMap.put(value, value1);
                    }
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(DbConfigurationEvent dbConfigurationEvent) {
        log.info("onApplicationEvent {}", dbConfigurationEvent);
        String propertyName = dbConfigurationEvent.getPropertyName();
        Object propertyValue = dbConfigurationEvent.getPropertyValue();

        final List<DbConfigProperty> dbConfigProperties = dbConfigPropertyMap.get(propertyName);
        if (ObjectUtils.isEmpty(dbConfigProperties)){
            return;
        }
        for (DbConfigProperty dbConfigProperty : dbConfigProperties) {
            if (ObjectUtils.isNotEmpty(dbConfigProperty)) {
                Field propertyField = dbConfigProperty.getPropertyField();
                Class<?> type = propertyField.getType();
                try {
                    propertyField.setAccessible(true);
                    propertyField.set(dbConfigProperty.getPropertyClass(), convertValue(type, propertyValue));

//              刷新所有的bean
//                final Map<String, DynamicRefreshService> beansOfType = applicationContext.getBeansOfType(DynamicRefreshService.class);
//                if (!beansOfType.isEmpty()){
//                    beansOfType.values().forEach(DynamicRefreshService::refresh);
//                }

                } catch (IllegalAccessException e) {
                    log.error("set property error {}", dbConfigurationEvent, e);
//                throw new RuntimeException(e);
                }
            }
        }
        dynamicRefreshScope.refreshAll();
    }


    public static Object convertValue(Class<?> targetType, Object value) {
        if (value == null) {
            return null;  // 如果传入值为 null，直接返回 null
        }
        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }

        if (targetType == String.class) {
            return value.toString();  // 转为 String
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(value.toString());  // 转为 Integer
        } else if (targetType == Long.class || targetType == long.class) {
            return Long.parseLong(value.toString());  // 转为 Long
        } else if (targetType == Double.class || targetType == double.class) {
            return Double.parseDouble(value.toString());  // 转为 Double
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.parseBoolean(value.toString());  // 转为 Boolean
        } else if (targetType == Float.class || targetType == float.class) {
            return Float.parseFloat(value.toString());  // 转为 Float
        } else if (targetType == Character.class || targetType == char.class) {
            return value.toString().charAt(0);  // 转为 Character
        } else if (targetType == Byte.class || targetType == byte.class) {
            return Byte.parseByte(value.toString());  // 转为 Byte
        } else if (targetType == Short.class || targetType == short.class) {
            return Short.parseShort(value.toString());  // 转为 Short
        } else {
            throw new IllegalArgumentException("Unsupported target type: " + targetType.getName());
        }
    }
}

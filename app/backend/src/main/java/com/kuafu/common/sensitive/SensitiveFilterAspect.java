package com.kuafu.common.sensitive;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
public class SensitiveFilterAspect {

    private final SensitiveWordFilter filter = new SensitiveWordFilter(); // 过滤器

    @Around("@annotation(com.kuafu.common.sensitive.SensitiveFilter)")
    public Object doFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs(); // 获取方法参数

        for (Object arg : args) {
            if (arg instanceof String) {
                arg = filter.filter((String) arg); // 处理字符串参数
            } else {
                filterSensitiveFields(arg); // 处理对象中的字符串字段
            }
        }

        return joinPoint.proceed(args); // 继续执行方法
    }

    private void filterSensitiveFields(Object obj) {
        if (obj == null) return;
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.getType() == String.class) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(obj);
                    if (value != null) {
                        field.set(obj, filter.filter(value)); // 替换敏感词
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
        }
    }
}

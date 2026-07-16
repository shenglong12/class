package com.kuafu.common.dynamic_config.config;

import com.kuafu.common.dynamic_config.annoation.DynamicRefreshScopeAnnotation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Deprecated
public class OrdersBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {
    private final DynamicRefreshScope dynamicRefreshScope;

    public OrdersBeanPostProcessor(DynamicRefreshScope dynamicRefreshScope) {
        this.dynamicRefreshScope = dynamicRefreshScope;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private final Set<String> beanNames = new HashSet<>();

    private final Map<String, Object> refreshScopeCache = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(DynamicRefreshScopeAnnotation.class)) {
            this.beanNames.add(beanName);
            refreshScopeCache.putIfAbsent(beanName, bean); // 保存最原始的bean
            return createRefreshProxy(bean, beanName, dynamicRefreshScope);
        }
        return bean;
    }

    private Object createRefreshProxy(Object bean, String beanName, DynamicRefreshScope scope) {
        Enhancer enhancer = new Enhancer();

        // 设置被代理的类
        enhancer.setSuperclass(bean.getClass());
        enhancer.setInterfaces(bean.getClass().getInterfaces());

        // 设置回调（相当于InvocationHandler）
        enhancer.setCallback((MethodInterceptor) (proxy, method, args, methodProxy) -> {
            // 每次调用都通过作用域获取最新实例
            Object target = scope.get(beanName, () -> bean);
//                    Object target = scope.get(beanName, () -> refreshScopeCache.get(beanName));

            // 使用Spring的ReflectionUtils调用方法
//                        return ReflectionUtils.invokeMethod(method, target, args);

            // 或者使用CGLIB的MethodProxy（性能更好）
            return method.invoke(target, args);
        });

        // 创建代理实例
        return enhancer.create();
    }
}

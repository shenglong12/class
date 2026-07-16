package com.kuafu.common.dynamic_config.config;

import com.kuafu.common.dynamic_config.annoation.DynamicRefreshScopeAnnotation;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Deprecated
//@Configuration
public class RefreshProxyConfiguration {

    @Bean
    public BeanPostProcessor refreshScopeProxyCreator(DynamicRefreshScope dynamicRefreshScope) {
        return new OrdersBeanPostProcessor(dynamicRefreshScope) ;


    }
}
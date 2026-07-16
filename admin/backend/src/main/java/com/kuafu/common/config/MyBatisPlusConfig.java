package com.kuafu.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置
 *
 * @author kuafu
 */
@Configuration
public class MyBatisPlusConfig {


    @Value("${spring.profiles.active}")
    private String dbType;

    /**
     * 拦截器配置
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        if ("mysql".equalsIgnoreCase(dbType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        } else if ("sqlite".equalsIgnoreCase(dbType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQLITE));
        }
        return interceptor;
    }
}
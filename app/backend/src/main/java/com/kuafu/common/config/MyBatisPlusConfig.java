package com.kuafu.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.kuafu.web.handler.CustomTenantHandler;
import com.kuafu.web.handler.TenantContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * MyBatis Plus 配置
 *
 * @author kuafu
 */
@Configuration
public class MyBatisPlusConfig {


    @Value("${spring.profiles.active}")
    private String dbType;
    private final boolean enableTenant = TenantContextHolder.getEnableTenant();
    @Resource
    private TenantContextHolder tenantContextHolder;

    /**
     * 拦截器配置
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 多租户插件
        if (enableTenant) {
            interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new CustomTenantHandler(tenantContextHolder)));
        }

        // 分页插件
        if ("mysql".equalsIgnoreCase(dbType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        } else if ("sqlite".equalsIgnoreCase(dbType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQLITE));
        }
        return interceptor;
    }
}
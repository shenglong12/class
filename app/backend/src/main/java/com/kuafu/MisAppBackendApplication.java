package com.kuafu;

import com.kuafu.common.schedule.config.SchedulingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableRetry
@Import(SchedulingConfig.class)
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableTransactionManagement
public class MisAppBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MisAppBackendApplication.class, args);
    }
}

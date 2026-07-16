package com.kuafu.common.ai_agent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AIAgentThreadPoolConfig {

    @Bean
    public TaskExecutor aiAgentTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(12); // 核心线程数设置为8
        executor.setMaxPoolSize(20); // 最大线程12
        executor.setQueueCapacity(1000); // 等待队列设置为200
        executor.setKeepAliveSeconds(3);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("agent-thread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}

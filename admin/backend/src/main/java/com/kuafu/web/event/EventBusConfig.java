package com.kuafu.web.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class EventBusConfig {

    @Bean("myAsyncEventBus")
    @Lazy(value = true)
    public EventBus createAsyncEventBus(ThreadPoolTaskExecutor threadPool) {
        return new AsyncEventBus(threadPool);
    }
}
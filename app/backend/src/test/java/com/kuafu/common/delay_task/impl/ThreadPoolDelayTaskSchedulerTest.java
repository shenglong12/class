package com.kuafu.common.delay_task.impl;

import com.kuafu.common.delay_task.DelayTaskScheduler;
import com.kuafu.common.delay_task.test.OrderDelayTask;
import com.kuafu.common.delay_task.test.OrderDelayTaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ThreadPoolDelayTaskSchedulerTest {

    @Resource
    private DelayTaskScheduler delayTaskScheduler;

    @Resource
    private OrderDelayTaskHandler orderDelayTaskHandler;

    @Test
    void schedule() throws IOException {


        log.info("schedule");
        for (int i = 0; i < 10; i++){
            final OrderDelayTask taskPram = new OrderDelayTask();
            taskPram.setOrderId(UUID.randomUUID().toString());
            delayTaskScheduler.schedule(taskPram, i, TimeUnit.SECONDS);
        }

        System.in.read();
    }
}
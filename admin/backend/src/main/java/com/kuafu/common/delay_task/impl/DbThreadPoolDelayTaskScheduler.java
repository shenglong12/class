package com.kuafu.common.delay_task.impl;

import com.kuafu.common.delay_task.DelayTaskScheduler;
import com.kuafu.common.delay_task.domain.DBDelayTask;
import com.kuafu.common.delay_task.domain.DelayTask;
import com.kuafu.common.delay_task.domain.TypeDelayTaskWrapper;
import com.kuafu.common.delay_task.handler.AbstractDelayedTaskHandler;
import com.kuafu.common.delay_task.handler.DBDelayedTaskHandlerWrapper;
import com.kuafu.common.delay_task.register.DelayTaskRegister;
import com.kuafu.common.delay_task.service.DelayedTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 操作结果将会放在数据库中
 */

@Component
@Primary // 默认使用这个
public class DbThreadPoolDelayTaskScheduler implements DelayTaskScheduler {


    private final ScheduledExecutorService scheduler;

    @Autowired
    private DelayTaskRegister delayTaskRegister;

    @Autowired
    @Qualifier("commonDelayedTasksService")
    private DelayedTasksService delayedTasksService;

    public DbThreadPoolDelayTaskScheduler(int poolSize) {
        this.scheduler = Executors.newScheduledThreadPool(poolSize);
    }

    public DbThreadPoolDelayTaskScheduler() {
        int poolSize = Runtime.getRuntime().availableProcessors();
        this.scheduler = Executors.newScheduledThreadPool(poolSize + 1);
    }

    @Override
    public void schedule(DelayTask taskPram, long delay, TimeUnit unit) {
        final Class<? extends DelayTask> aClass = taskPram.getClass();
        final String taskName = aClass.getSimpleName();

        AbstractDelayedTaskHandler delayedTaskHandler = delayTaskRegister.getHandler(aClass);
        final Integer taskId = delayedTasksService.saveDelayTaskToDb(taskPram, delay, unit, taskName);

//      构建包装类
        final DBDelayTask dbDelayTask = new DBDelayTask(taskId, taskPram);
        final DBDelayedTaskHandlerWrapper dbAbstractDelayedTaskHandler = new DBDelayedTaskHandlerWrapper(delayedTaskHandler, delayedTasksService);

        scheduler.schedule(new TypeDelayTaskWrapper(dbAbstractDelayedTaskHandler,
                dbDelayTask), delay, unit);
    }






}

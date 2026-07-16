package com.kuafu.common.delay_task;

import com.kuafu.common.delay_task.handler.AbstractDelayedTaskHandler;
import com.kuafu.common.delay_task.domain.DelayTask;


import java.util.concurrent.TimeUnit;

public interface DelayTaskScheduler {


    /**
     * 提交一个延迟任务
     *
     *
     * @param delay 延迟时间
     * @param unit  延迟时间单位
     */

    void schedule(DelayTask taskPram, long delay, TimeUnit unit);

}

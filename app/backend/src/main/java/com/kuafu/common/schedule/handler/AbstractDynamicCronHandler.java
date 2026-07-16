package com.kuafu.common.schedule.handler;

import com.kuafu.common.schedule.manger.DynamicCronScheduleTaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

public abstract class AbstractDynamicCronHandler {

    @Autowired
    protected DynamicCronScheduleTaskManager dynamicCronScheduleTaskManager;
    /**
     * 获取cron表达式
     * @return
     */
    public abstract String getCronExpression(String cronName);

    /**
     * 更新cronName对应的定时任务的触发时机
     * @param cronName
     */
    public void updateTriggerTask(String cronName) {
        dynamicCronScheduleTaskManager.updateTriggerTask(cronName);
    }


}

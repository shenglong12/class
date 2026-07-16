package com.kuafu.common.schedule.manger;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务的管理器（单任务版，每个 cronName 只支持一个任务）
 */
public class DynamicCronScheduleTaskManager implements DisposableBean {

    private ScheduledTaskRegistrar registrar;

    // 保存任务名对应的 ScheduledTask
    private final Map<String, ScheduledTask> dynamicScheduledTaskMap = new ConcurrentHashMap<>();

    public void setRegistrar(ScheduledTaskRegistrar registrar) {
        this.registrar = registrar;
    }


    //    public DynamicCronScheduleTaskManager(ScheduledTaskRegistrar registrar) {
//        this.registrar = registrar;
//    }


    public DynamicCronScheduleTaskManager() {
    }

    /**
     * 添加或覆盖定时任务
     *
     * @param cronName 任务名
     * @param task     TriggerTask 任务
     * @return
     */
    public ScheduledTask addTriggerTask(String cronName, TriggerTask task) {
        ScheduledTask oldTask = dynamicScheduledTaskMap.get(cronName);
        if (oldTask != null) {
            oldTask.cancel();
        }

        ScheduledTask newTask = this.registrar.scheduleTriggerTask(task);
        dynamicScheduledTaskMap.put(cronName, newTask);
        return newTask;
    }

    /**
     * 判断是否包含指定任务名
     */
    public boolean contains(String cronName) {
        return this.dynamicScheduledTaskMap.containsKey(cronName);
    }

    /**
     * 更新定时任务的触发时机（实际上就是替换掉）
     */
    // 更新定时任务的触发时机
    public void updateTriggerTask(String cronName) {
        ScheduledTask scheduledTask = dynamicScheduledTaskMap.get(cronName);
        if (scheduledTask == null) {
            throw new IllegalStateException("Invalid cronName" + cronName + ",no fund ScheduledTask");
        }
        scheduledTask.cancel();

        scheduledTask = this.registrar.scheduleTriggerTask((TriggerTask) scheduledTask.getTask());
        dynamicScheduledTaskMap.put(cronName, scheduledTask);
    }


    /**
     * 移除定时任务
     */
    public void removeTriggerTask(String cronName) {
        ScheduledTask oldTask = dynamicScheduledTaskMap.remove(cronName);
        if (oldTask != null) {
            oldTask.cancel();
        }
    }

    /**
     * 容器销毁时，取消所有任务
     */
    @Override
    public void destroy() {
        dynamicScheduledTaskMap.values().forEach(ScheduledTask::cancel);
        dynamicScheduledTaskMap.clear();
    }
}

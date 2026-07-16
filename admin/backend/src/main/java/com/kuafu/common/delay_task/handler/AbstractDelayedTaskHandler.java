package com.kuafu.common.delay_task.handler;

import com.kuafu.common.delay_task.domain.DelayTask;

/**
 * 延迟任务
 */
public abstract class AbstractDelayedTaskHandler<T extends DelayTask> {

    /**
     * 获取支持的参数类型（用于类型检查）
     */
    public abstract Class<?> getSupportedType();


    /**
     * 抽象处理方法
     *
     *
     */
    public abstract void handler(T param);



}

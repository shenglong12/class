package com.kuafu.common.delay_task.domain;

import com.kuafu.common.delay_task.handler.AbstractDelayedTaskHandler;

/**
 * 包装类
 */
public class TypeDelayTaskWrapper implements Runnable {
    private final AbstractDelayedTaskHandler handler;
    private final DelayTask taskParam;

    public TypeDelayTaskWrapper(AbstractDelayedTaskHandler handler, DelayTask taskParam) {
        this.handler = handler;
        this.taskParam = taskParam;
    }

    @Override
    public void run() {
        handler.handler(taskParam);
    }
}

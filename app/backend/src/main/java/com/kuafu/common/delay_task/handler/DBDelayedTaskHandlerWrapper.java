package com.kuafu.common.delay_task.handler;

import com.kuafu.common.delay_task.constant.DelayTaskConstant;
import com.kuafu.common.delay_task.domain.DBDelayTask;
import com.kuafu.common.delay_task.domain.DelayTask;
import com.kuafu.common.delay_task.domain.DelayedTasks;
import com.kuafu.common.delay_task.service.DelayedTasksService;

import java.time.LocalDateTime;


/**
 * db 延迟任务的处理类
 */
public class DBDelayedTaskHandlerWrapper extends AbstractDelayedTaskHandler<DelayTask> {

    private final AbstractDelayedTaskHandler abstractDelayedTaskHandler;

    private final DelayedTasksService delayedTasksService;

    public DBDelayedTaskHandlerWrapper(AbstractDelayedTaskHandler abstractDelayedTaskHandler,
                                       DelayedTasksService delayedTasksService) {
        this.abstractDelayedTaskHandler = abstractDelayedTaskHandler;
        this.delayedTasksService = delayedTasksService;
    }

    /**
     * 获取支持的参数类型（用于类型检查）
     */
    public Class<?> getSupportedType() {
        return abstractDelayedTaskHandler.getSupportedType();
    }


    /**
     * 抽象处理方法
     */
    public void handler(DelayTask param) {
        if (param instanceof DBDelayTask) {
            DBDelayTask dbDelayTask = (DBDelayTask) param;
            try {
                delayedTasksService.updateTaskResult(dbDelayTask, DelayTaskConstant.RUNNING);
//              走原来的逻辑
                abstractDelayedTaskHandler.handler(dbDelayTask.getTaskPram());
//              更新任务结果为成功
                delayedTasksService.updateTaskResult(dbDelayTask, DelayTaskConstant.SUCCESS);
            } catch (Exception e) {
                delayedTasksService.updateTaskResult(dbDelayTask, DelayTaskConstant.FAIL);
                throw e;
            }
        } else {
            abstractDelayedTaskHandler.handler(param);
        }

    }


}

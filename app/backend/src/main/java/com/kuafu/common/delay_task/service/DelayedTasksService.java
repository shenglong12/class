package com.kuafu.common.delay_task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuafu.common.delay_task.domain.DBDelayTask;
import com.kuafu.common.delay_task.domain.DelayTask;
import com.kuafu.common.delay_task.domain.DelayedTasks;

import java.util.concurrent.TimeUnit;

/**
* @author www.macpe.cn
* @description 针对表【delayed_tasks】的数据库操作Service
* @createDate 2025-05-09 14:24:05
*/
public interface DelayedTasksService extends IService<DelayedTasks> {

    Integer saveDelayTaskToDb(DelayTask taskPram, long delay, TimeUnit unit, String taskName);

    Boolean updateTaskResult(DBDelayTask dbDelayTask, String status);
}

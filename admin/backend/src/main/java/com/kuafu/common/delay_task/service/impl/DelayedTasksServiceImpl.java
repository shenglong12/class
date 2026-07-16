package com.kuafu.common.delay_task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuafu.common.delay_task.constant.DelayTaskConstant;
import com.kuafu.common.delay_task.domain.DBDelayTask;
import com.kuafu.common.delay_task.domain.DelayTask;
import com.kuafu.common.delay_task.domain.DelayedTasks;
import com.kuafu.common.delay_task.mapper.DelayedTasksMapper;
import com.kuafu.common.delay_task.service.DelayedTasksService;
import com.kuafu.common.util.JSON;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
* @author www.macpe.cn
* @description 针对表【delayed_tasks】的数据库操作Service实现
* @createDate 2025-05-09 14:24:05
*/
@Service("commonDelayedTasksService")
public class DelayedTasksServiceImpl extends ServiceImpl<DelayedTasksMapper, DelayedTasks>
    implements DelayedTasksService {


    /**
     * 保存数据到数据库
     *
     * @param taskPram
     * @param delay
     * @param unit
     * @param taskName
     */

    @Override
    public Integer saveDelayTaskToDb(DelayTask taskPram, long delay, TimeUnit unit, String taskName) {
        final DelayedTasks delayedTasks = new DelayedTasks();
        delayedTasks.setTaskType(taskName);
        delayedTasks.setTaskData(JSON.toJSONString(taskPram));
        delayedTasks.setStatus(DelayTaskConstant.PENDING);
        delayedTasks.setCreateTime(LocalDateTime.now());
        delayedTasks.setUpdateTime(LocalDateTime.now());
        delayedTasks.setExecuteTime(LocalDateTime.now().plus(delay, toChronoUnit(unit)));
        final boolean save = save(delayedTasks);
        return delayedTasks.getId();
    }


    @Override

    public Boolean updateTaskResult(DBDelayTask dbDelayTask, String status) {
        //          更新任务结果
        final DelayedTasks entity = new DelayedTasks();
        entity.setId(dbDelayTask.getTaskId());
        entity.setStatus(status);
        entity.setUpdateTime(LocalDateTime.now());
        return updateById(entity);
    }


    public static TemporalUnit toChronoUnit(TimeUnit timeUnit) {
        switch (timeUnit) {
            case NANOSECONDS:
                return ChronoUnit.NANOS;
            case MICROSECONDS:
                return ChronoUnit.MICROS;
            case MILLISECONDS:
                return ChronoUnit.MILLIS;
            case SECONDS:
                return ChronoUnit.SECONDS;
            case MINUTES:
                return ChronoUnit.MINUTES;
            case HOURS:
                return ChronoUnit.HOURS;
            case DAYS:
                return ChronoUnit.DAYS;
            default:
                throw new IllegalArgumentException("Unsupported TimeUnit: " + timeUnit);
        }
    }

}





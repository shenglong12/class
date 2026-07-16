package com.kuafu.common.schedule.annotation;

import com.kuafu.common.schedule.handler.AbstractDynamicCronHandler;


import java.lang.annotation.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduledDynamicCron {

    /**
     * cornName的名称
     * @return
     */
    String cornName();





    /**
     * 动态cron处理类
     * @return
     */
    Class<? extends AbstractDynamicCronHandler> handler();

}

package com.kuafu.common.schedule.config;

import com.kuafu.common.schedule.manger.DynamicCronScheduleTaskManager;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.TaskManagementConfigUtils;

@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class SchedulingConfig  {





    @Bean
    public DynamicCronScheduleTaskManager dynamicCronScheduleTaskManager(){
        return new DynamicCronScheduleTaskManager();
    }

    /**
     * 覆盖默认的 ScheduledAnnotationBeanPostProcessor
     * @return
     */
    @Bean(name = TaskManagementConfigUtils.SCHEDULED_ANNOTATION_PROCESSOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ScheduledAnnotationBeanPostProcessor scheduledAnnotationProcessor(DynamicCronScheduleTaskManager dynamicCronScheduleTaskManager) {
        return new DynamicScheduledAnnotationBeanPostProcessor(dynamicCronScheduleTaskManager);
    }


}
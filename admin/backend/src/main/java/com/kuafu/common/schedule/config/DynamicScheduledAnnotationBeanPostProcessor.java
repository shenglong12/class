package com.kuafu.common.schedule.config;

import com.kuafu.common.schedule.annotation.ScheduledDynamicCron;
import com.kuafu.common.schedule.manger.DynamicCronScheduleTaskManager;
import com.kuafu.common.schedule.support.DynamicCronTrigger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.*;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicScheduledAnnotationBeanPostProcessor extends ScheduledAnnotationBeanPostProcessor {

    private final DynamicCronScheduleTaskManager dynamicCronScheduleTaskManager;

    public static final String DEFAULT_TASK_SCHEDULER_BEAN_NAME = "taskScheduler";


    protected final Log logger = LogFactory.getLog(getClass());

    private final ScheduledTaskRegistrar registrar = getRegistrar();

    @Nullable
    private Object scheduler;

    @Nullable
    private StringValueResolver embeddedValueResolver;

    @Nullable
    private String beanName;

    @Nullable
    private BeanFactory beanFactory;

    @Nullable
    private ApplicationContext applicationContext;

    private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(64));

    private final Map<Object, Set<ScheduledTask>> scheduledTasks = new IdentityHashMap<>(16);


    public DynamicScheduledAnnotationBeanPostProcessor(DynamicCronScheduleTaskManager dynamicCronScheduleTaskManager) {
        super();
        this.dynamicCronScheduleTaskManager = dynamicCronScheduleTaskManager;
        this.dynamicCronScheduleTaskManager.setRegistrar(this.registrar);
    }


    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        super.setEmbeddedValueResolver(resolver);
        this.embeddedValueResolver = resolver;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.beanFactory = beanFactory;
    }


    @Override
    protected void processScheduled(Scheduled scheduled, Method method, Object bean) {
        try {
            Runnable runnable = createRunnable(bean, method);
            boolean processedSchedule = false;
            String errorMessage =
                    "Exactly one of the 'cron', 'fixedDelay(String)', or 'fixedRate(String)' attributes is required";

            Set<ScheduledTask> tasks = new LinkedHashSet<>(4);

            // Determine initial delay
            long initialDelay = scheduled.initialDelay();
            String initialDelayString = scheduled.initialDelayString();
            if (StringUtils.hasText(initialDelayString)) {
                Assert.isTrue(initialDelay < 0, "Specify 'initialDelay' or 'initialDelayString', not both");
                if (this.embeddedValueResolver != null) {
                    initialDelayString = this.embeddedValueResolver.resolveStringValue(initialDelayString);
                }
                if (StringUtils.hasLength(initialDelayString)) {
                    try {
                        initialDelay = parseDelayAsLong(initialDelayString);
                    } catch (RuntimeException ex) {
                        throw new IllegalArgumentException(
                                "Invalid initialDelayString value \"" + initialDelayString + "\" - cannot parse into long");
                    }
                }
            }

            // Check cron expression
            String cron = scheduled.cron();
            // 处理ScheduledDynamicCron注解
            if (method.isAnnotationPresent(ScheduledDynamicCron.class)) {
                ScheduledDynamicCron dynamicCron = method.getAnnotation(ScheduledDynamicCron.class);
                String cronName = dynamicCron.cornName(); // 这是动态定时任务名称
                if (StringUtils.hasText(dynamicCron.cornName())) {
                    cronName = dynamicCron.cornName();
                } else {
                    Assert.isTrue(StringUtils.hasText(cronName), "@ScheduledDynamicCron 'cronName'  attributes is required");
                }

                DynamicCronTrigger trigger = new DynamicCronTrigger(cronName, this.beanFactory.getBean(dynamicCron.handler()));

                ScheduledTask scheduledTask = this.dynamicCronScheduleTaskManager.addTriggerTask(cronName, new TriggerTask(runnable, trigger));
                tasks.add(scheduledTask);

                processedSchedule = true;
            }else {
                if (StringUtils.hasText(cron)) {
                    String zone = scheduled.zone();
                    if (this.embeddedValueResolver != null) {
                        cron = this.embeddedValueResolver.resolveStringValue(cron);
                        zone = this.embeddedValueResolver.resolveStringValue(zone);
                    }
                    if (StringUtils.hasLength(cron)) {
                        Assert.isTrue(initialDelay == -1, "'initialDelay' not supported for cron triggers");
                        processedSchedule = true;
                        if (!Scheduled.CRON_DISABLED.equals(cron)) {
                            TimeZone timeZone;
                            if (StringUtils.hasText(zone)) {
                                timeZone = StringUtils.parseTimeZoneString(zone);
                            } else {
                                timeZone = TimeZone.getDefault();
                            }
                            tasks.add(this.registrar.scheduleCronTask(new CronTask(runnable, new CronTrigger(cron, timeZone))));
                        }
                    }
                }
            }


            // At this point we don't need to differentiate between initial delay set or not anymore
            if (initialDelay < 0) {
                initialDelay = 0;
            }

            // Check fixed delay
            long fixedDelay = scheduled.fixedDelay();
            if (fixedDelay >= 0) {
                Assert.isTrue(!processedSchedule, errorMessage);
                processedSchedule = true;
                tasks.add(this.registrar.scheduleFixedDelayTask(new FixedDelayTask(runnable, fixedDelay, initialDelay)));
            }
            String fixedDelayString = scheduled.fixedDelayString();
            if (StringUtils.hasText(fixedDelayString)) {
                if (this.embeddedValueResolver != null) {
                    fixedDelayString = this.embeddedValueResolver.resolveStringValue(fixedDelayString);
                }
                if (StringUtils.hasLength(fixedDelayString)) {
                    Assert.isTrue(!processedSchedule, errorMessage);
                    processedSchedule = true;
                    try {
                        fixedDelay = parseDelayAsLong(fixedDelayString);
                    } catch (RuntimeException ex) {
                        throw new IllegalArgumentException(
                                "Invalid fixedDelayString value \"" + fixedDelayString + "\" - cannot parse into long");
                    }
                    tasks.add(this.registrar.scheduleFixedDelayTask(new FixedDelayTask(runnable, fixedDelay, initialDelay)));
                }
            }
            // Check fixed rate
            long fixedRate = scheduled.fixedRate();
            if (fixedRate >= 0) {
                Assert.isTrue(!processedSchedule, errorMessage);
                processedSchedule = true;
                tasks.add(this.registrar.scheduleFixedRateTask(new FixedRateTask(runnable, fixedRate, initialDelay)));
            }
            String fixedRateString = scheduled.fixedRateString();
            if (StringUtils.hasText(fixedRateString)) {
                if (this.embeddedValueResolver != null) {
                    fixedRateString = this.embeddedValueResolver.resolveStringValue(fixedRateString);
                }
                if (StringUtils.hasLength(fixedRateString)) {
                    Assert.isTrue(!processedSchedule, errorMessage);
                    processedSchedule = true;
                    try {
                        fixedRate = parseDelayAsLong(fixedRateString);
                    } catch (RuntimeException ex) {
                        throw new IllegalArgumentException(
                                "Invalid fixedRateString value \"" + fixedRateString + "\" - cannot parse into long");
                    }
                    tasks.add(this.registrar.scheduleFixedRateTask(new FixedRateTask(runnable, fixedRate, initialDelay)));
                }
            }

            // Check whether we had any attribute set
            Assert.isTrue(processedSchedule, errorMessage);

            // Finally register the scheduled tasks
            synchronized (this.scheduledTasks) {
                Set<ScheduledTask> regTasks = this.scheduledTasks.computeIfAbsent(bean, key -> new LinkedHashSet<>(4));
                regTasks.addAll(tasks);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException(
                    "Encountered invalid @Scheduled method '" + method.getName() + "': " + ex.getMessage());
        }
    }


    private static long parseDelayAsLong(String value) throws RuntimeException {
        if (value.length() > 1 && (isP(value.charAt(0)) || isP(value.charAt(1)))) {
            return Duration.parse(value).toMillis();
        }
        return Long.parseLong(value);
    }

    private static boolean isP(char ch) {
        return (ch == 'P' || ch == 'p');
    }

    /**
     * 找到父类的属性设置给子类
     *
     * @return
     */
    private ScheduledTaskRegistrar getRegistrar() {
        Field field = null;
        try {
            field = ScheduledAnnotationBeanPostProcessor.class.getDeclaredField("registrar");
            field.setAccessible(true);
            return (ScheduledTaskRegistrar) field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


}

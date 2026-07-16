package com.kuafu.common.schedule.support;

import com.kuafu.common.schedule.handler.AbstractDynamicCronHandler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronExpression;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 触发器
 */
public class DynamicCronTrigger implements Trigger {

    private final String cornName;



    private final ZoneId zoneId;


    private final AbstractDynamicCronHandler abstractDynamicCronHandler;


    public DynamicCronTrigger(String cornName, ZoneId zoneId, AbstractDynamicCronHandler abstractDynamicCronHandler) {
        this.cornName = cornName;
        this.zoneId = zoneId;
        this.abstractDynamicCronHandler = abstractDynamicCronHandler;
    }


    public DynamicCronTrigger(String cornName, AbstractDynamicCronHandler abstractDynamicCronHandler) {
        this.cornName = cornName;
        this.zoneId=ZoneId.systemDefault();
        this.abstractDynamicCronHandler = abstractDynamicCronHandler;
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {

        Date date = triggerContext.lastCompletionTime();
        if (date != null) {
            Date scheduled = triggerContext.lastScheduledExecutionTime();
            if (scheduled != null && date.before(scheduled)) {
                // Previous task apparently executed too early...
                // Let's simply use the last calculated execution time then,
                // in order to prevent accidental re-fires in the same second.
                date = scheduled;
            }
        }
        else {
            date = new Date();
        }
//      动态获取cron表达式
        String cornExpression = abstractDynamicCronHandler.getCronExpression(cornName);

        CronExpression expression= CronExpression.parse(cornExpression);

        ZonedDateTime dateTime = ZonedDateTime.ofInstant(date.toInstant(), this.zoneId);
        ZonedDateTime next = expression.next(dateTime);
        return next != null ? Date.from(next.toInstant()) : null;

    }


}

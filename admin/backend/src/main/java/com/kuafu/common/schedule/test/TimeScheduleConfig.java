package com.kuafu.common.schedule.test;

import com.kuafu.common.dynamic_config.annoation.DBConfiguration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@DBConfiguration
@Data
public class TimeScheduleConfig {
    @Value("${log:0/1 * * * * ?}")
    private String cron;
}

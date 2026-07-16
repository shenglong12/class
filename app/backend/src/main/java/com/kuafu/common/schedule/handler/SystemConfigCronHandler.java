package com.kuafu.common.schedule.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.common.dynamic_config.domain.SystemConfig;
import com.kuafu.common.dynamic_config.service.SystemConfigService;

import com.kuafu.common.schedule.test.TimeScheduleConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Component
@Slf4j
public class SystemConfigCronHandler extends AbstractDynamicCronHandler {

    @Resource
    private TimeScheduleConfig timeScheduleConfig;

    @Override
    public String getCronExpression(String cronName) {
        return timeScheduleConfig.getCron();
    }
}

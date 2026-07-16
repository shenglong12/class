package com.kuafu.common.dynamic_config.schedule;

import com.kuafu.common.dynamic_config.config.DBConfigGlobalConfig;
import com.kuafu.common.schedule.handler.AbstractDynamicCronHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DbRefreshConfigureScheduleCornHandler extends AbstractDynamicCronHandler {


    @Resource
    private DBConfigGlobalConfig dbConfigGlobalConfig;
    @Override
    public String getCronExpression(String cronName) {


        return dbConfigGlobalConfig.getRefreshInterval();
    }
}

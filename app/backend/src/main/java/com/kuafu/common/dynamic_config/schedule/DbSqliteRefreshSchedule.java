package com.kuafu.common.dynamic_config.schedule;

import com.kuafu.common.dynamic_config.domain.SystemConfig;
import com.kuafu.common.dynamic_config.event.puhlisher.DbConfigurationRefreshPublisher;
import com.kuafu.common.dynamic_config.service.SystemConfigService;
import com.kuafu.common.schedule.annotation.ScheduledDynamicCron;

import com.kuafu.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
//@ConditionalOnProperty(name = "spring.profiles.active",havingValue = "sqlite")
public class DbSqliteRefreshSchedule {

    @Resource
    private SystemConfigService systemConfigService;

    private Map<String, String> dbConfigMap = new ConcurrentHashMap<>();

    @Resource
    private DbConfigurationRefreshPublisher dbConfigurationRefreshPublisher;

    @Scheduled
    @ScheduledDynamicCron(cornName = "dbconfig.refreshInterval", handler = DbRefreshConfigureScheduleCornHandler.class)
    public void refresh() {
        log.info("refresh");
        List<SystemConfig> list = systemConfigService.list();
        if (dbConfigMap.isEmpty()) {
            for (SystemConfig systemConfig : list) {
                if (StringUtils.isNotEmpty(systemConfig.getContent()) && StringUtils.isNotEmpty(systemConfig.getName())) {
                    // 初始化时也发送一下
                    dbConfigMap.put(systemConfig.getName(), systemConfig.getContent());
                    dbConfigurationRefreshPublisher.publishEvent(systemConfig.getName(), systemConfig.getContent());

                }


            }
            return;
        }

//      比较是否有变化
        for (SystemConfig systemConfig : list) {
            String name = systemConfig.getName();
            if (!dbConfigMap.containsKey(name)) {
                if (StringUtils.isNotEmpty(systemConfig.getContent()) && StringUtils.isNotEmpty(systemConfig.getName())) {
                    dbConfigMap.put(name, systemConfig.getContent());
                    dbConfigurationRefreshPublisher.publishEvent(name, systemConfig.getContent());
                }
                continue;
            }

            String value = dbConfigMap.get(name);

            if (!value.equals(systemConfig.getContent())) {
                log.info("{} changed from {} to {}", name, value, systemConfig.getContent());
//              说明发生了变化,更新系统的属性值
                dbConfigurationRefreshPublisher.publishEvent(name, systemConfig.getContent());
            }
        }


//      清除旧的，缓存新的
        dbConfigMap.clear();


        for (SystemConfig systemConfig : list) {
            if (StringUtils.isNotEmpty(systemConfig.getContent()) && StringUtils.isNotEmpty(systemConfig.getName())) {
                dbConfigMap.put(systemConfig.getName(), systemConfig.getContent());
//                dbConfigurationRefreshPublisher.publishEvent(systemConfig.getName(), systemConfig.getContent());

            }
        }


    }
}

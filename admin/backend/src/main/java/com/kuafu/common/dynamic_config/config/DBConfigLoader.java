package com.kuafu.common.dynamic_config.config;


import com.kuafu.web.entity.KfSystemConfig;
import com.kuafu.web.service.IKfSystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化加载配置
 */
@Component
@Slf4j
@DependsOn(value = {"flywayInitializer"})
public class DBConfigLoader  {

    @Autowired
    private IKfSystemConfigService systemConfigService;

    @Autowired
    private ConfigurableEnvironment environment;

    @PostConstruct
    public void run() {

        log.info("开始加载数据库配置...");
        List<KfSystemConfig> systemConfigs = systemConfigService.list();

        if (ObjectUtils.isEmpty(systemConfigs)){
            return;
        }
        Map<String, Object> props = new HashMap<>();
        for (KfSystemConfig p : systemConfigs) {
            props.put(p.getName(), p.getContent());
        }
        // 添加到环境中
        MapPropertySource propertySource = new MapPropertySource("dbConfig", props);

        environment.getPropertySources().addFirst(propertySource);

        log.info("结束加载数据库配置...");
    }
}


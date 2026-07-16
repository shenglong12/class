package com.kuafu.common.dynamic_config.config;

import com.kuafu.common.dynamic_config.annoation.DBConfiguration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@DBConfiguration
public class DBConfigGlobalConfig {
    @Value("${db.config.global.refreshInterval:0/5 * * * * ?}")
    public String refreshInterval;
}

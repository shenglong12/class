package com.kuafu.pay.business;

import com.kuafu.common.dynamic_config.annoation.DynamicRefreshScopeAnnotation;
import com.kuafu.common.util.UUID;
import org.springframework.stereotype.Component;

@Component
@DynamicRefreshScopeAnnotation
public class TestBeanConfig {
    private String name;


    public TestBeanConfig() {
        this.name = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }
}

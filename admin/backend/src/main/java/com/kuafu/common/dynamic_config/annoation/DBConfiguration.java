package com.kuafu.common.dynamic_config.annoation;


import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 用于标注数据库配置的类
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@DependsOn("DBConfigLoader") // 等待DBConfigLoader加载完成，后再加载对应的bean
public @interface DBConfiguration {

}

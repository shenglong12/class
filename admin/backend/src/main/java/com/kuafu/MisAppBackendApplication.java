package com.kuafu;

import com.kuafu.common.schedule.config.SchedulingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
@Import(SchedulingConfig.class)
public class MisAppBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MisAppBackendApplication.class, args);
    }
}

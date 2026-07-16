package com.kuafu.common.delay_task.test;


import com.kuafu.common.delay_task.domain.DelayTask;
import lombok.Data;

@Data
public class OrderDelayTask extends DelayTask {
    private String orderId;
}

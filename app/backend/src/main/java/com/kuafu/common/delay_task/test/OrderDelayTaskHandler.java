package com.kuafu.common.delay_task.test;

import com.kuafu.common.delay_task.handler.AbstractDelayedTaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderDelayTaskHandler extends AbstractDelayedTaskHandler<OrderDelayTask> {
    @Override
    public void handler(OrderDelayTask delayTask) {
        // 具体处理逻辑
//        System.out.println("处理订单延迟任务: " + delayTask.getOrderId());

        log.info("处理订单延迟任务: " + delayTask.getOrderId());
    }
    @Override
    public Class<?> getSupportedType() {
        return OrderDelayTask.class;
    }



}
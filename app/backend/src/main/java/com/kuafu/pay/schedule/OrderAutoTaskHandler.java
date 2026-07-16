package com.kuafu.pay.schedule;

import com.kuafu.common.schedule.handler.AbstractDynamicCronHandler;
import com.kuafu.pay.business.config.OrderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderAutoTaskHandler extends AbstractDynamicCronHandler {
    @Autowired
    private OrderConfig orderConfig;

    @Override
    public String getCronExpression(String cronName) {
        return orderConfig.getAutomaticScheduleCorn();
    }
}

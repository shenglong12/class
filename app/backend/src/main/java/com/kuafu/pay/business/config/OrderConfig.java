package com.kuafu.pay.business.config;

import com.kuafu.common.dynamic_config.annoation.DBConfiguration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@DBConfiguration
@Data
public class OrderConfig {
    /**
     * 订单未支付的过期时间，单位分钟
     */
    @Value("${order.expire.time:1}")
    private Integer orderExpireTime;

    /**
     * 订单自动收货时间，单位天
     */
    @Value("${order.automatic.receiptTime:3}")
    private Integer automaticReceiptTime;


    @Value("${order.automatic.schedule.corn:0 0 3 * * ?}")

    private String automaticScheduleCorn;
}

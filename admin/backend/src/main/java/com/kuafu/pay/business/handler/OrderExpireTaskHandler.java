package com.kuafu.pay.business.handler;

import com.kuafu.common.delay_task.handler.AbstractDelayedTaskHandler;
import com.kuafu.pay.business.GeneralOrderBusinessService;
import com.kuafu.pay.business.domain.OrderExpireTaskParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class OrderExpireTaskHandler extends AbstractDelayedTaskHandler<OrderExpireTaskParam> {


    @Resource
    private GeneralOrderBusinessService generalOrderBusinessService;
    @Override
    public Class<?> getSupportedType() {
        return OrderExpireTaskParam.class;
    }

    @Override
    public void handler(OrderExpireTaskParam param) {
        log.info("订单超时,关闭订单,订单号:{}", param.getOrderNo());
//      关闭订单
        generalOrderBusinessService.expireOrder(param.getOrderNo());

        log.info("订单超时处理完成,订单号:{}", param.getOrderNo());
    }
}

package com.kuafu.pay.schedule;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.kuafu.common.schedule.annotation.ScheduledDynamicCron;
import com.kuafu.pay.business.config.OrderConfig;
import com.kuafu.pay.db.domain.GeneralOrders;
import com.kuafu.pay.db.service.GeneralOrdersService;
import com.kuafu.pay.enums.PayStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderAutoReceiptScheduleTask {

    @Autowired
    @Qualifier("payGeneralOrdersService")
    private  GeneralOrdersService generalOrdersService;
    @Autowired
    private  OrderConfig orderConfig;

    @Scheduled // 每天凌晨 3 点执行
    @ScheduledDynamicCron(cornName = "order.automatic.schedule.corn",handler = OrderAutoTaskHandler.class) // 每天凌晨 3 点执行
    public void autoConfirmReceipt() {
        log.info("开始执行自动确认收货任务");

        LocalDateTime deadline = LocalDateTime.now().minusDays(orderConfig.getAutomaticReceiptTime());

        // 查找超时未确认收货的订单
        List<GeneralOrders> overdueOrders = generalOrdersService.lambdaQuery()
                .eq(GeneralOrders::getOrderStatus, PayStatus.PENDING_RECEIPT)
                .le(GeneralOrders::getDeliverTime, deadline)
                .list();

        if (CollectionUtils.isEmpty(overdueOrders)) {
            log.info("没有超时未收货订单，任务结束");
            return;
        }

        for (GeneralOrders order : overdueOrders) {
            LambdaUpdateWrapper<GeneralOrders> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(GeneralOrders::getId, order.getId())
                    .eq(GeneralOrders::getOrderStatus, PayStatus.PENDING_RECEIPT)
                    .set(GeneralOrders::getOrderStatus, PayStatus.CONFIRM)
                    .set(GeneralOrders::getCompleteTime, LocalDateTime.now());

            boolean success = generalOrdersService.update(updateWrapper);
            if (success) {
                log.info("自动确认收货成功, orderNo={}", order.getOrderNo());
            } else {
                log.warn("自动确认收货失败, orderNo={}", order.getOrderNo());
            }
        }
    }
}


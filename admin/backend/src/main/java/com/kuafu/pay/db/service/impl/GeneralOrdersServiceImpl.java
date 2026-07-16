package com.kuafu.pay.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.pay.db.domain.GeneralOrders;
import com.kuafu.pay.db.mapper.GeneralOrdersMapper;
import com.kuafu.pay.db.service.GeneralOrdersService;
import com.kuafu.pay.enums.PayStatus;
import com.kuafu.pay.stats.PayStatusStateMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author www.macpe.cn
 * @description 针对表【general_orders】的数据库操作Service实现
 * @createDate 2025-05-09 17:20:09
 */
@Service("payGeneralOrdersService")
@Slf4j
public class GeneralOrdersServiceImpl extends ServiceImpl<GeneralOrdersMapper, GeneralOrders>
        implements GeneralOrdersService {

    @Override
    public GeneralOrders getByOrderNo(String orderNo) {
        final LambdaQueryWrapper<GeneralOrders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GeneralOrders::getOrderNo, orderNo)
                .or()
                .eq(GeneralOrders::getPaymentOrderId,orderNo);
        return getOne(queryWrapper);
    }

    @Override
    public GeneralOrders getByPaymentOrderId(String paymentOrderId) {
        final LambdaQueryWrapper<GeneralOrders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(GeneralOrders::getPaymentOrderId, paymentOrderId)
                .or()
                .eq(GeneralOrders::getOrderNo,paymentOrderId);
        return getOne(queryWrapper);
    }

    @Override
    public GeneralOrders getByRefundId(String refundId) {
        final LambdaQueryWrapper<GeneralOrders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GeneralOrders::getRefundId, refundId);
        return getOne(queryWrapper);
    }

    @Override
    public void updatePaymentStatusByPaymentOrderId(String paymentOrderId, PayStatus originalStatus, PayStatus newStatus) {


        if (PayStatusStateMachine.canTransition(originalStatus, newStatus)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "订单状态更新异常，不允许更新");
        }

        final LambdaUpdateWrapper<GeneralOrders> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GeneralOrders::getPaymentOrderId, paymentOrderId)
                .set(GeneralOrders::getOrderStatus, newStatus)
                .eq(GeneralOrders::getOrderStatus, originalStatus);
        final boolean update = update(updateWrapper);
        if (!update) {
            log.error("updatePaymentStatusByPaymentOrderId error {},originalStatus {},newStatus {}", paymentOrderId, originalStatus, newStatus);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "订单状态更新失败");
        }
    }

    @Override
    public void updatePaymentStatusByOrderNo(String orderNo, PayStatus originalStatus, PayStatus newStatus) {
        if (PayStatusStateMachine.canTransition(originalStatus, newStatus)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "订单状态更新异常，不允许更新");
        }

        final LambdaUpdateWrapper<GeneralOrders> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GeneralOrders::getOrderNo, orderNo)
                .set(GeneralOrders::getOrderStatus, newStatus)
                .eq(GeneralOrders::getOrderStatus, originalStatus);
        final boolean update = update(updateWrapper);
        if (!update) {
            log.error("updatePaymentStatusByOrderNo status error {},originalStatus {},newStatus {}", orderNo, originalStatus, newStatus);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "订单状态更新失败");
        }
    }

    @Override
    public GeneralOrders getByOpId(String opId) {

        final LambdaQueryWrapper<GeneralOrders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GeneralOrders::getOpId, opId);
        return getOne(queryWrapper);

    }
}





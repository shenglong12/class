package com.kuafu.pay.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.pay.business.domain.OrderCreatRequest;
import com.kuafu.pay.domain.PayCallbackRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OrderFacadeService {

    @Resource
    private GeneralOrderBusinessService orderBusinessService;

    @Resource
    private ObjectMapper objectMapper;

    public BaseResponse<?> handleOrder(String operateName, Map<String, Object> body) {
        try {
            switch (operateName) {
                case "getUniqueOrderNo":
                    return getUniqueOrderNo(body);
                case "create":
                    return createOrder(body);
                case "deliver":
                    return deliverOrder(body);
                case "confirm":
                    return confirmOrder(body);
                case "getPaymentParam":
                    return getPaymentParam(body);
                case "getPayOrderMessage":
                    return getPayOrderMessage(body);
                case "cancelPay":
                    return cancelPay(body);
                case "refund":
                    return refund(body);
                default:
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的操作类型: " + operateName);
            }
        } catch (Exception e) {
            log.error("操作处理失败: {}", operateName, e);
            throw e;
        }
    }

    private BaseResponse<?> refund(Map<String, Object> body) {

        return ResultUtils.success(orderBusinessService.refund(String.valueOf(body.get("id"))));
    }

    private BaseResponse<?> cancelPay(Map<String, Object> body) {
        Object deliverOrderNo = body.get("orderNo");

        if (ObjectUtils.isEmpty(deliverOrderNo)) {

            deliverOrderNo = body.get("paymentOrderId");
        }


        return ResultUtils.success(orderBusinessService.cancelPay(String.valueOf(deliverOrderNo)));
    }

    private BaseResponse<?> getPayOrderMessage(Map<String, Object> body) {
        Object deliverOrderNo = body.get("orderNo");

        if (ObjectUtils.isEmpty(deliverOrderNo)) {

            deliverOrderNo = body.get("paymentOrderId");
        }



        return ResultUtils.success(orderBusinessService.getOrder(String.valueOf(deliverOrderNo)));

    }

    private BaseResponse<?> getUniqueOrderNo(Map<String, Object> body) {
        String productId = String.valueOf(body.get("productId"));
        String userId = String.valueOf(body.get("userId"));

        return ResultUtils.success(orderBusinessService.getUniqueOrderNo(productId,
                userId));
    }

    // 创建订单
    private BaseResponse<?> createOrder(Map<String, Object> body) {
        OrderCreatRequest createRequest = objectMapper.convertValue(body,
                OrderCreatRequest.class);
        return ResultUtils.success(orderBusinessService.createPaymentOrder(createRequest));
    }

//

    // 发货
    private BaseResponse<?> deliverOrder(Map<String, Object> body) {
        Object deliverOrderNo = body.get("orderNo");

        if (ObjectUtils.isEmpty(deliverOrderNo)) {

            deliverOrderNo = body.get("paymentOrderId");
        }


        orderBusinessService.deliverOrder(String.valueOf(deliverOrderNo));
        return ResultUtils.success(true);
    }

    // 确认收货
    private BaseResponse<?> confirmOrder(Map<String, Object> body) {
        Object deliverOrderNo = body.get("orderNo");
        if (ObjectUtils.isEmpty(deliverOrderNo)) {
            deliverOrderNo = body.get("paymentOrderId");
        }
        orderBusinessService.confirmReceipt(String.valueOf(deliverOrderNo));
        return ResultUtils.success(true);
    }

    /**
     * 获取支付参数
     *
     * @param body
     * @return
     */
    private BaseResponse<?> getPaymentParam(Map<String, Object> body) {
        Object deliverOrderNo = body.get("orderNo");
        if (ObjectUtils.isEmpty(deliverOrderNo)) {
            deliverOrderNo = body.get("paymentOrderId");
        }
        return ResultUtils.success(orderBusinessService.getPaymentParam(String.valueOf(deliverOrderNo)));
    }

    /**
     * 处理回调
     *
     * @param payChannel
     * @param requestData
     * @param headers
     * @return
     */
    public Object handleOrderCallback(String payChannel, String requestData, Map<String, String> headers) {
        PayCallbackRequest payCallbackRequest = orderBusinessService.decryption(payChannel, requestData, headers);
        log.info("解密后的支付参数{}",payCallbackRequest);
        return orderBusinessService.paySuccessCallback(payCallbackRequest);

    }
}

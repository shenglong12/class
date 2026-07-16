package com.kuafu.pay.business;

import com.kuafu.pay.business.domain.OrderCreatRequest;
import com.kuafu.pay.db.domain.GeneralOrders;
import com.kuafu.pay.db.service.GeneralOrdersService;
import com.kuafu.pay.domain.PayCallbackRequest;
import com.kuafu.pay.enums.PayStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

@SpringBootTest
class GeneralOrderBusinessServiceTest {

    @Resource
    private GeneralOrderBusinessService  generalOrderBuinessService;

    @Resource
    private GeneralOrdersService generalOrdersService;
    @Test
    void createPaymentOrder() throws IOException {
        final GeneralOrders entity = new GeneralOrders();
        entity.setOrderNo("1");
        entity.setOpId("1111");
        entity.setProductsId(1);
        entity.setUserId(1);
        entity.setOrderStatus(PayStatus.UNPAID);
        entity.setPayStatus(PayStatus.UNPAID);
        entity.setTotalAmount(new BigDecimal("0"));
        entity.setActualAmount(new BigDecimal("0"));
        entity.setCompleteTime(LocalDateTime.now());
        final boolean save = generalOrdersService.save(entity);
//        final String uniqueOrderNo = generalOrderBuinessService.getUniqueOrderNo("1", "1");
//
//        final OrderCreatRequest orderCreatRequest = new OrderCreatRequest();
//        orderCreatRequest.setProductId(1);
//        orderCreatRequest.setUserId(1);
//        orderCreatRequest.setPayChanel("mock");
//        orderCreatRequest.setOpId(uniqueOrderNo);
//        orderCreatRequest.setQuality(10);
//        orderCreatRequest.setPriceSingle(new BigDecimal("12"));
//        orderCreatRequest.setRemark("放在门口");
//        orderCreatRequest.setProductSubject("测试商品");
//        orderCreatRequest.setTel("12345678901");
//        orderCreatRequest.setRecipient("张三");
//        orderCreatRequest.setShippingAddress("上海");
//         Object createPaymentOrder = generalOrderBuinessService.createPaymentOrder(orderCreatRequest);
//        System.out.println(createPaymentOrder);
//        final Object createPaymentOrder2 = generalOrderBuinessService.createPaymentOrder(orderCreatRequest);
//        System.out.println(createPaymentOrder2);
//
//        final PayCallbackRequest payCallbackRequest = new PayCallbackRequest();
//        payCallbackRequest.setPaymentOrderId(createPaymentOrder.toString());
//        final Object paySuccessCallback = generalOrderBuinessService
//                .paySuccessCallback(payCallbackRequest);
////        System.in.read()
//        final GeneralOrders byPaymentOrderId = generalOrdersService.getByPaymentOrderId(createPaymentOrder.toString());
//        generalOrderBuinessService.deliverOrder(byPaymentOrderId.getOrderNo());

    }

    @Test
    void getUniqueOrderNo() {
    }

    @Test
    void paySuccessCallback() {
        final PayCallbackRequest payCallbackRequest = new PayCallbackRequest();
        payCallbackRequest.setPaymentOrderId("8c0fc00f-659e-4cb5-8241-5c002caace08");
        final Object paySuccessCallback = generalOrderBuinessService
                .paySuccessCallback(payCallbackRequest);
        System.out.println(paySuccessCallback);
    }

    @Test
    void expireOrder() {
    }

    @Test
    void deliverOrder() {
        generalOrderBuinessService.deliverOrder("1921841778483634176");
    }

    @Test
    void confirmReceipt() {
        generalOrderBuinessService.confirmReceipt("1921831595552739328");
    }

    @Test
    void getOrder() {
    }

    @Test
    void getPaymentParam() {
    }

    @Test
    void decryption() {
    }

    @Test
    void toPascalCase() {
    }

    @Test
    void cancelPay() {
        generalOrderBuinessService.cancelPay("1922230346048880640");
    }

    @Test
    void updatePaymentOrdersIdToBusinessOrder() {
    }

    @Test
    void testPaySuccessCallback() {
        final PayCallbackRequest payCallbackRequest = new PayCallbackRequest();
        payCallbackRequest.setPaymentOrderId("4200002685202505155049021477");
        payCallbackRequest.setOrderNo("65");
        payCallbackRequest.setPaymentTime(LocalDateTime.now());
        payCallbackRequest.setPaymentAmount(new BigDecimal("0.1"));
        payCallbackRequest.setPayStatus(PayStatus.PAID_SUCCESS);
        payCallbackRequest.setExtraParamMap(new HashMap<>());


        generalOrderBuinessService.paySuccessCallback(payCallbackRequest);
    }
}
package com.kuafu.pay.business;

import com.kuafu.pay.db.domain.GeneralOrders;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeneralOrderBusinessServiceTest {

    @Resource
    private GeneralOrderBusinessService generalOrderBusinessService;

    @Test
    void refund() {
        final Boolean refund = generalOrderBusinessService.refund("106");
        System.out.println(refund);
    }

    @Test
    void getUniqueOrderNo() {
    }

    @Test
    void getOrder() {
        GeneralOrders order = generalOrderBusinessService.getOrder("10");
        System.out.println(order);
    }

    @Test
    void createPaymentOrder() {
    }

    @Test
    void updatePaymentOrdersIdToBusinessOrder() {
    }

    @Test
    void getPaymentParam() {
    }

    @Test
    void paySuccessCallback() {
    }

    @Test
    void expireOrder() {
    }

    @Test
    void deliverOrder() {
    }

    @Test
    void confirmReceipt() {
    }

    @Test
    void decryption() {
    }

    @Test
    void toPascalCase() {
    }

    @Test
    void cancelPay() {
    }
}
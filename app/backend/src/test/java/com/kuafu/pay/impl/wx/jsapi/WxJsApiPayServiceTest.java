package com.kuafu.pay.impl.wx.jsapi;

import com.kuafu.common.util.SnowflakeIdGenerator;
import com.kuafu.common.util.UUID;
import com.kuafu.login.domain.Login;
import com.kuafu.login.service.ILoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WxJsApiPayServiceTest {
    @Autowired
    private WxJsApiPayService wxJsApiPayService;


    @Autowired
    private ILoginService loginService;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Test
    void createPaymentOrder() {
        final Login byId = loginService.getById(1);

        System.out.println(wxJsApiPayService.createPaymentOrder(byId, String.valueOf(snowflakeIdGenerator.nextId()), new BigDecimal("3"),
                "测试支付"
                , null));
    }

    @Test
    void getPaymentParam() {
    }

    @Test
    void queryPaymentStatus() {
    }

    @Test
    void cancelPaymentOrder() {
    }

    @Test
    void applyRefund() {
    }

    @Test
    void queryRefundStatus() {
    }

    @Test
    void processPaymentCallback() {
    }

    @Test
    void processRefundCallback() {
    }

    @Test
    void closePaymentOrder() {
        wxJsApiPayService.closePaymentOrder("1","67");
    }

    @Test
    void getPaymentOrderDetail() {
    }

    @Test
    void payCallbackProcessSuccess() {
    }

    @Test
    void payCallbackProcessFail() {
    }

    @Test
    void callbackDecryption() {
    }
}
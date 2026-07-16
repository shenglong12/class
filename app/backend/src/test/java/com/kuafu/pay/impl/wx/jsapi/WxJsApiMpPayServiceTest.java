package com.kuafu.pay.impl.wx.jsapi;

import com.kuafu.login.domain.Login;
import com.kuafu.login.service.ILoginService;
import com.kuafu.pay.config.WxV3PayConfig;
import com.kuafu.pay.factoary.PayFactory;
import com.kuafu.pay.service.PayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WxJsApiMpPayServiceTest {
//    @Autowired
//    private WxJsApiMpPayService wxJsApiMpPayService;
    @Autowired
    private ILoginService loginService;

    @Autowired
    private PayFactory payFactory;
    @Autowired
    private WxV3PayConfig wxV3PayConfig;


    @Test
    void tedst(){
        wxV3PayConfig.readPrivateKeyContent(wxV3PayConfig.getPrivateKey());
    }

    @Test
    void getAppId() throws IOException, InterruptedException {


//        System.out.println();
        for (int i=0;i<100;i++){
            try {
                PayService wxJsApiMpPayService = payFactory.getPayService("wx_jsapi_mp");
                final Login byId = loginService.getById(1);
                System.out.println(wxJsApiMpPayService.createPaymentOrder(byId, "9283828383123",
                        new BigDecimal("0.01"), "测试", null));

            }catch (Exception e){

            }
            Thread.sleep(5000L);

        }
    }
}
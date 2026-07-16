package com.kuafu.pay.controller;

import com.google.common.collect.Lists;
import com.kuafu.common.annotation.Log;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.pay.business.GeneralOrderBusinessService;
import com.kuafu.pay.business.OrderFacadeService;
import com.kuafu.pay.config.WxV3PayConfig;
import com.kuafu.pay.domain.OrderOperateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/generalOrder")
@Slf4j
public class GeneralOrderBusinessController {


    @Resource
    private OrderFacadeService orderFacadeService;



    @Resource
    private WxV3PayConfig wxV3PayConfig;


    /**
     * 订单处理的统一入口
     * @param
     * @return
     */
    @PostMapping("/{operateName}")
    @Log
    public BaseResponse<?> handleOrder(@PathVariable String operateName, @RequestBody
                                       Map<String, Object> body) {

        return orderFacadeService.handleOrder(operateName, body);

    }

    @GetMapping("/payMethod")
    @Log
    public BaseResponse<?> payMethod() {
        final ArrayList<String> strings = Lists.newArrayList();
        final Boolean wxEnable = wxV3PayConfig.getWxEnable();
        final Boolean mockEnable = wxV3PayConfig.getMockEnable();

        if (wxEnable) {
            strings.add("wechat");
        }


        if (mockEnable) {
            strings.add("mock");
        }

        return ResultUtils.success(strings);

    }


    @PostMapping("/callback/{payChannel}")
    @Log
    public Object paySuccessCallback(@PathVariable String payChannel,@RequestBody String requestData, @RequestHeader Map<String, String> headers) {
        log.info("paySuccessCallback payChannel:{},requestData:{},headers:{}", payChannel, requestData, headers);
        return orderFacadeService.handleOrderCallback(payChannel,requestData,headers);
    }
}

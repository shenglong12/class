package com.kuafu.pay.factoary;

import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.util.SpringUtils;
import com.kuafu.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PayFactory {


    public PayService getPayService(String payChannel) {
        try {
            final PayService bean = SpringUtils.getBean(payChannel);
            return bean;
        } catch (Exception e) {

           log.error("getPayService", e);
            throw new BusinessException(payChannel + "not found");
        }

    }

}

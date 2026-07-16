package com.kuafu.pay.impl.wx.jsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.dynamic_config.annoation.DynamicRefreshScopeAnnotation;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.util.RandomStringUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.login.domain.Login;
import com.kuafu.pay.config.WxV3PayConfig;
import com.kuafu.pay.domain.PayCallbackRequest;
import com.kuafu.pay.domain.PaymentOrderDetail;
import com.kuafu.pay.enums.PayStatus;
import com.kuafu.pay.service.PayService;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.TransactionAmount;
import com.wechat.pay.java.service.refund.RefundService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众号的服务
 */
@Component("wx_jsapi_mp")
@DynamicRefreshScopeAnnotation
public class WxJsApiMpPayService extends WxJsApiPayService {


    private WxV3PayConfig wxV3PayConfig;



    @Autowired
    public WxJsApiMpPayService( WxV3PayConfig wxV3PayConfig) {
        super(wxV3PayConfig);
        this.wxV3PayConfig = wxV3PayConfig;
    }

    public WxJsApiMpPayService() {
        super();
    }

    public String getAppId() {
        return wxV3PayConfig.getMpAppId();
    }
}

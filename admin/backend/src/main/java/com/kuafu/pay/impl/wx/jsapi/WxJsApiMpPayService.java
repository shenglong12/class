package com.kuafu.pay.impl.wx.jsapi;

import com.kuafu.common.dynamic_config.annoation.DynamicRefreshScopeAnnotation;
import com.kuafu.pay.config.WxV3PayConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 微信公众号的服务
 */
@Component("wx_jsapi_mp")
@DynamicRefreshScopeAnnotation
public class WxJsApiMpPayService extends WxJsApiPayService {


    private WxV3PayConfig wxV3PayConfig;


    public WxJsApiMpPayService(@Autowired WxV3PayConfig wxV3PayConfig) {
        super(wxV3PayConfig);
        this.wxV3PayConfig = wxV3PayConfig;
    }


    public String getAppId() {
        return wxV3PayConfig.getMpAppId();
    }
}

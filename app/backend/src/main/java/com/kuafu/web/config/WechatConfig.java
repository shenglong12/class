package com.kuafu.web.config;


import com.kuafu.common.dynamic_config.annoation.DBConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@DBConfiguration
public class WechatConfig {

    @Value("${wechat.app-id:xxxx}")
    private String appId;

    @Value("${wechat.app-secret:xxxx}")
    private String appSecret;

    @Value("${wechat.is-register:0}")
    private String isRegister;
    @Value("${wx.pay.mp-app-id:xxxxx}")
    private String mpAppId;

    @Value("${wechat.mp-redirect_uri:xxxxx}")
    private String mpRedirectUri;

    @Value("${wx.pay.mp-app-secret:xxx}")
    private String mpAppSecret;

    @Value("${wechat.mp-frontend_redirect_uri:xxxx}")
    private String mpFrontendRedirectUri;

    public String getMpFrontendRedirectUri() {
        return mpFrontendRedirectUri;
    }

    public void setMpFrontendRedirectUri(String mpFrontendRedirectUri) {
        this.mpFrontendRedirectUri = mpFrontendRedirectUri;
    }

    public String getMpAppSecret() {
        return mpAppSecret;
    }

    public void setMpAppSecret(String mpAppSecret) {
        this.mpAppSecret = mpAppSecret;
    }

    public String getMpRedirectUri() {
        return mpRedirectUri;
    }

    public void setMpRedirectUri(String mpRedirectUri) {
        this.mpRedirectUri = mpRedirectUri;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getIsRegister() {
        return isRegister;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setIsRegister(String isRegister) {
        this.isRegister = isRegister;
    }

    public String getMpAppId() {
        return mpAppId;
    }

    public void setMpAppId(String mpAppId) {
        this.mpAppId = mpAppId;
    }
}


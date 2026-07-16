package com.kuafu.pay.config;



import com.kuafu.common.dynamic_config.annoation.DBConfiguration;
import com.kuafu.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Configuration
@DBConfiguration
public class WxV3PayConfig {

    /**
     * 微信回调通知地址
     */
    @Value("${wx.pay.pay-back-url:xxxx}")
    private String payBackUrl;

    @Value("${wx.pay.mch-serial-no:xxxx}")
    private String mchSerialNo;

    @Value("${wx.pay.app-id:xxxx}")
    private String appId;

    @Value("${wx.pay.mp-app-id:xxxx}")
    private String mpAppId;

    @Value("${wx.pay.mch-id:xxxx}")
    private String mchId;

    @Value("${wx.pay.api-v3-key:xxxx}")
    private String apiV3Key;

    @Value("${wx.pay.private-key:xxxx}")
    private String privateKey;

//    wx.pay.is_test
//    wx.pay.is_test_amount

    @Value("${wx.pay.is_test:1}")
    private Boolean isTest;

    @Value("${wx.pay.is_test_amount:0.01}")
    private String isTestAmount;

    @Value("${wx.pay.wechat_enable:false}")
    private Boolean wxEnable;

    @Value("${wx.pay.mock_enable:false}")
    private Boolean mockEnable;


    @Value("${wx.pay.order_pre_key:000000-}")
    private String orderPreKey;


    public String getOrderPreKey() {
        return orderPreKey;
    }

    public void setOrderPreKey(String orderPreKey) {
        this.orderPreKey = orderPreKey;
    }

    public Boolean getMockEnable() {
        return mockEnable;
    }

    public void setMockEnable(Boolean mockEnable) {
        this.mockEnable = mockEnable;
    }

    public Boolean getWxEnable() {
        return wxEnable;
    }

    public void setWxEnable(Boolean wxEnable) {
        this.wxEnable = wxEnable;
    }

    public String getMpAppId() {
        return mpAppId;
    }

    public void setMpAppId(String mpAppId) {
        this.mpAppId = mpAppId;
    }

    public String getPayBackUrl() {
        return payBackUrl;
    }

    public void setPayBackUrl(String payBackUrl) {
        this.payBackUrl = payBackUrl;
    }

    public String getMchSerialNo() {
        return mchSerialNo;
    }

    public void setMchSerialNo(String mchSerialNo) {
        this.mchSerialNo = mchSerialNo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getApiV3Key() {
        return apiV3Key;
    }

    public void setApiV3Key(String apiV3Key) {
        this.apiV3Key = apiV3Key;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;


    }

    public Boolean getTest() {
        return isTest;
    }

    public void setTest(Boolean test) {
        isTest = test;
    }

    public String getIsTestAmount() {
        return isTestAmount;
    }

    public void setIsTestAmount(String isTestAmount) {
        this.isTestAmount = isTestAmount;
    }

    public static PrivateKey readPrivateKeyContent(String privateKey) {
        Security.setProperty("crypto.policy", "unlimited");

        try {
//            String content = new String(Files.readAllBytes(Paths.get(privateKey)), "utf-8");
            String content = privateKey; // 在此处使用私钥文件内容
            String privateKeyContent = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");


            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }

    }

}


package com.kuafu.login.service;

import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.wx.WxAppClient;
import com.kuafu.common.wx.WxWebCode2TokenRequest;
import com.kuafu.common.wx.WxWebCode2TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "login", name = "enable")
public class WxWebService {
    @Value("${wx.web.id:id}")
    private String appId;
    @Value("${wx.web.secret:secret}")
    private String appSecret;

    private WxAppClient wxAppClient = new WxAppClient();

    public String getOpenId(String code) {
        WxWebCode2TokenResponse response = wxAppClient.code2Session(WxWebCode2TokenRequest.builder()
                .appId(appId)
                .appSecret(appSecret)
                .code(code)
                .grantType("authorization_code")
                .build());

        log.info("=============== {}", response);
        if (response.getErrcode() != null && response.getErrcode() > 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR.getCode(), "H5验证失败");
        }
        return response.getOpenId();
    }

}

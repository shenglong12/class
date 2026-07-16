package com.kuafu.login.service;

import com.kuafu.common.cache.Cache;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.util.StringUtils;
import com.kuafu.common.wx.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(prefix = "login", name = "enable")
public class WxAppService {

    @Value("${wx.app.id:id}")
    private String appId;
    @Value("${wx.app.secret:secret}")
    private String appSecret;

    private WxAppClient wxAppClient = new WxAppClient();

    @Resource
    private Cache cache;

    private static final String KEY = "wx:accesstoken";

    public String getOpenId(String code) {
        WxAppCode2SessionResponse response = wxAppClient.code2Session(WxAppCode2SessionRequest.builder()
                .appId(appId)
                .appSecret(appSecret)
                .code(code)
                .grantType("authorization_code")
                .build());
        if (response.getErrcode() != null && response.getErrcode() > 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR.getCode(), "小程序验证失败");
        }
        return response.getOpenId();
    }

    public String getAccessToken() {

        String accessToken = cache.getCacheObject(KEY);

        if (StringUtils.isNotEmpty(accessToken)) {
            return accessToken;
        }

        WxAppAccessTokenRequest request = WxAppAccessTokenRequest.builder()
                .appId(appId)
                .appSecret(appSecret)
                .grantType("client_credential")
                .build();

        WxAppAccessTokenResponse response = wxAppClient.getAccessToken(request);
        if (response.getErrcode() != null && response.getErrcode() != 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "token获取失败");
        }

        accessToken = response.getAccessToken();
        int expires_in = response.getExpiresIn();
        // 写入缓存中
        cache.setCacheObject(KEY, accessToken, expires_in, TimeUnit.SECONDS);

        return accessToken;
    }


    /**
     * 获取手机号
     *
     * @param code 授权码
     * @return 手机号
     */
    public String getPhone(String code) {
        String accessToken = getAccessToken();

        WxAppPhoneNumberRequest request = WxAppPhoneNumberRequest.builder()
                .accessToken(accessToken)
                .code(code)
                .build();

        WxAppPhoneNumberResponse response = wxAppClient.getPhoneNumber(request);

        if (response.getErrcode() != null && response.getErrcode() != 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "手机号获取失败");
        }

        return response.getPhoneInfo().getPhoneNumber();

    }
}

package com.kuafu.login.controller;

import cn.hutool.http.HttpUtil;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.util.JSON;
import com.kuafu.common.util.StringUtils;
import com.kuafu.web.config.WechatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
@Slf4j
public class RedirectController {
    @Resource
    private WechatConfig wechatConfig;

    @GetMapping("login/redirect")
    public String redirect(@RequestParam String code) {
        log.info("【微信登录】,code:{}", code);
        final String mpAppId = wechatConfig.getMpAppId();
        final String mpAppSecret = wechatConfig.getMpAppSecret();

        String urlTemplate = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code";

        final String format = StringUtils.format(urlTemplate, mpAppId, mpAppSecret, code);
        final String string = HttpUtil.post(format, new HashMap<>());
        final Map map = JSON.parseObject(string, Map.class);
        String openid = null;
        if (map.containsKey("openid")) {
            openid = String.valueOf(map.get("openid"));
        } else {
            log.error("【获取openid失败】,code:{},string:{}", code, string);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, String.valueOf(map.get("errmsg")));
        }
        String redictUrl=wechatConfig.getMpFrontendRedirectUri() + "?openid=" + openid;

        log.info("【微信登录】,code:{},openid:{},redictUrl:{}", code, openid,redictUrl);

        return "redirect:" + redictUrl;


    }
}

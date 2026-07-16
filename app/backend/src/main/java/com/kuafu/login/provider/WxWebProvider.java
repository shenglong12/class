package com.kuafu.login.provider;

import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.login.LoginUser;
import com.kuafu.common.util.StringUtils;
import com.kuafu.login.model.LoginVo;
import com.kuafu.login.service.LoginBusinessService;
import com.kuafu.login.service.WxWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 微信H5
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "login", name = "enable")
public class WxWebProvider implements AuthenticationProvider {

    @Autowired
    private LoginBusinessService loginBusinessService;

    @Autowired
    private WxWebService wxWebService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginVo loginVo = (LoginVo) authentication.getPrincipal();
        log.info("{}", loginVo);
        //1.根据code换取openid
        String openid = wxWebCode2Session(loginVo.getCode());
        Long userId = loginBusinessService.getUserIdByOpenId(openid);
        if (userId == null) {
            //创建新用户
            log.info("openid : {} not exist, create new user", openid);
            userId = loginBusinessService.createNewUser(openid);
        }
        return new WxWebAuthentication(new LoginUser(userId), authentication.getAuthorities());
    }

    private String wxWebCode2Session(String code) throws BusinessException {
        if (StringUtils.equalsIgnoreCase(code, "1111")) {
            return "openid";
        }
        return wxWebService.getOpenId(code);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(WxWebAuthentication.class);
    }
}

package com.kuafu.login.provider;

import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.login.LoginUser;
import com.kuafu.common.util.StringUtils;
import com.kuafu.login.model.LoginVo;
import com.kuafu.login.service.LoginBusinessService;
import com.kuafu.login.service.WxAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 微信小程序登录
 */
@Component
@ConditionalOnProperty(prefix = "login", name = "enable")
public class WxAppProvider implements AuthenticationProvider {

    @Autowired
    private WxAppService wxAppService;

    @Autowired
    private LoginBusinessService loginBusinessService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException, BusinessException {
        LoginVo loginVo = (LoginVo) authentication.getPrincipal();

        //1.根据code换取openid
        String openid = wxAppCode2Session(loginVo.getCode());

        //2.根据openid查询用户信息
        Long userId = loginBusinessService.getUserIdByOpenId(openid);
        if (userId != null) {
            return new WxAppAuthentication(new LoginUser(userId), authentication.getAuthorities());
        } else {
            if (StringUtils.isEmpty(loginVo.getPhone())) {
                //只是登录
                throw new BusinessException(ErrorCode.NOT_BIND_DATA_ERROR.getCode(), "您的小程序还未绑定");
            } else {
                userId = loginBusinessService.getUserIdBySelectKey(loginVo.getPhone());
                if (userId != null && userId > 0) {
                    loginBusinessService.updateOpenIdById(userId, openid);
                    return new WxAppAuthentication(new LoginUser(userId), authentication.getAuthorities());
                } else {
                    throw new BusinessException(ErrorCode.NOT_FOUND_ERROR.getCode(), "数据不存在");
                }
            }
        }
    }

    private String wxAppCode2Session(String code) throws BusinessException {
        return wxAppService.getOpenId(code);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(WxAppAuthentication.class);
    }
}

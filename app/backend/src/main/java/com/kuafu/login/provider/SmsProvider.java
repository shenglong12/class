package com.kuafu.login.provider;

import com.kuafu.common.cache.Cache;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.login.LoginUser;
import com.kuafu.login.constant.LoginConstant;
import com.kuafu.login.model.LoginVo;
import com.kuafu.login.service.LoginBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 微信小程序登录
 */
@Component
@ConditionalOnProperty(prefix = "login", name = "enable")
public class SmsProvider implements AuthenticationProvider {

    @Autowired
    private Cache cache;

    @Autowired
    private LoginBusinessService loginBusinessService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException, BusinessException {
        LoginVo loginVo = (LoginVo) authentication.getPrincipal();

        final String phone = loginVo.getPhone(); // 手机号
        final String code = loginVo.getCode(); // code

        String cacheCode = cache.getCacheObject(LoginConstant.LOGIN_CACHE_PRE + phone);
        if (StringUtils.isEmpty(cacheCode)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "验证码不存在或已过期,请重新获取");
        }

        if (!StringUtils.endsWithIgnoreCase(cacheCode, code)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "验证码错误,请重试");
        }

        Object current = loginBusinessService.getUserBySelectKey(phone);
        if (current == null) {
//            新用户
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "用户不存在");
        }

        final Long userId = loginBusinessService.getUserIdBySelectKey(current);
        final String relevanceTable = loginBusinessService.getValue(current, "relevanceTable").toString();
//      获取关联表的id
        String relevanceId = Optional.ofNullable(loginBusinessService.getValue(current, "relevanceId"))
                .map(Object::toString).orElse(null);

        return new SmsAuthentication(new LoginUser(userId,relevanceId,relevanceTable), authentication.getAuthorities());

    }


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(SmsAuthentication.class);
    }
}

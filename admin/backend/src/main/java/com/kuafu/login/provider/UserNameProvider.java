package com.kuafu.login.provider;

import com.kuafu.common.login.LoginUser;
import com.kuafu.common.login.SecurityUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.login.service.LoginBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(prefix = "login", name = "enable")
@Slf4j
public class UserNameProvider implements AuthenticationProvider {

    @Autowired
    private LoginBusinessService loginBusinessService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getPrincipal().toString();
        String passwd = authentication.getCredentials().toString();

        Object current = loginBusinessService.getUserBySelectKey(userName);
        if (current == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        String dbPassword = loginBusinessService.password(current);
        if (StringUtils.isEmpty(dbPassword)) {
            throw new BadCredentialsException("密码有误");
        }

        if (!SecurityUtils.matchesPassword(passwd, dbPassword)) {
            throw new BadCredentialsException("密码有误");
        }

        Long userId = loginBusinessService.getId(current);

        return new UsernamePasswordAuthenticationToken(new LoginUser(userId), "", authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

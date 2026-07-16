package com.kuafu.login.provider;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SmsAuthentication extends AbstractAuthenticationToken {

    private final Object principal;

    public SmsAuthentication(Object principal) {
        super(null);
        this.principal = principal;
        super.setAuthenticated(false);
    }

    public SmsAuthentication(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}

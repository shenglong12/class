package com.kuafu.common.login;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LoginUser implements UserDetails {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;


    private String relevanceId;


    private String relevanceTable;

    private Integer tenantId;

    public String getRelevanceTable() {
        return relevanceTable;
    }

    public void setRelevanceTable(String relevanceTable) {
        this.relevanceTable = relevanceTable;
    }

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public LoginUser(Long userId, String relevanceId, String relevanceTable) {
        this.userId = userId;
        this.relevanceId = relevanceId;
        this.relevanceTable = relevanceTable;
    }


    public LoginUser(Long id, String relevanceId) {
        this.userId = id;
        this.relevanceId = relevanceId;
    }

    public LoginUser(Long id) {
        this.userId = id;
    }

    public LoginUser(Integer id) {
        this.userId = id.longValue();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}

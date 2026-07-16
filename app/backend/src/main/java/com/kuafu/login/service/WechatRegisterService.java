package com.kuafu.login.service;

import com.kuafu.common.util.StringUtils;
import com.kuafu.web.handler.CustomTenantHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 微信小程序注册事物
 */
@Component
@ConditionalOnProperty(prefix = "login", name = "enable")
public class WechatRegisterService {
    @Resource
    private LoginBusinessService loginBusinessService;

    @Transactional
    public long wechatRegister(String phone, String relevanceTable) {
        CustomTenantHandler.threadLocalSet.get().add(StringUtils.toUnderScoreCase(relevanceTable));
        Long relevanceId = loginBusinessService.insertRelevanceInfo(phone, relevanceTable);
        CustomTenantHandler.threadLocalSet.get().remove(StringUtils.toUnderScoreCase(relevanceTable));
        return relevanceId;
    }
}

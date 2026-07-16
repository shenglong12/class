package com.kuafu.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.kuafu.common.api.client.ApiClient;
import com.kuafu.common.api.spec.ApiDefinition;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.entity.DynamicApiSetting;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.resource.service.DynamicApiSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiBusinessService {

    private final DynamicApiSettingService dynamicApiSettingService;
    private final ApiClient apiClient = new ApiClient();

    public String callApi(String apiKey, Map<String, Object> params) {
        // 查询 api 记录
        DynamicApiSetting setting = getByApiKey(apiKey);
        if (setting == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (StringUtils.equalsIgnoreCase(setting.getProtocol(), "http")) {
            return callHttpApi(setting, params);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    public String callHttpApi(DynamicApiSetting setting, Map<String, Object> params) {

        ApiDefinition apiDefinition = getApiBySetting(setting);
        Map<String, String> fixedParams = Maps.newHashMap();
        // 判断鉴权方式
        fixedParams.put("token", setting.getToken());

        // 合并 params
        params.putAll(fixedParams);

        return apiClient.call(apiDefinition, params);
    }

    public DynamicApiSetting getByApiKey(String apiKey) {
        LambdaQueryWrapper<DynamicApiSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DynamicApiSetting::getKeyName, apiKey);
        return dynamicApiSettingService.getOne(queryWrapper);
    }

    private ApiDefinition getApiBySetting(DynamicApiSetting setting) {
        return ApiDefinition.builder()
                .name(setting.getKeyName())
                .method(setting.getMethod())
                .url(setting.getUrl())
                .headers(setting.getHeader())
                .bodyType(setting.getBodyType())
                .bodyTemplate(setting.getBodyTemplate())
                .build();
    }
}

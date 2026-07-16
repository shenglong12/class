package com.kuafu.api.domain;

import lombok.Data;

import java.util.Map;

@Data
public class ApiProxyRequest {
    /**
     * apiId
     */
    private String apiId;
    /**
     * 请求参数
     */
    private Map<String, Object> params;
}

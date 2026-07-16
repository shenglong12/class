package com.kuafu.api.service;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kuafu.api.config.ApiProxyConfig;
import com.kuafu.api.constant.HeaderTypeConstant;
import com.kuafu.api.constant.ParamTypeConstant;
import com.kuafu.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Configuration
@Slf4j
public class ApiProxyService {

    @Resource
    private ApiProxyConfig apiProxyConfig;

    @Resource
    private OkHttpClient okHttpClient;


    private static final Gson gson = new Gson();


    public void init() {
        log.info("init {}", apiProxyConfig);
    }


    /**
     * 所有请求的统一入口
     *
     * @param apiId
     * @param params
     * @return
     */
    public Map<String, Object> proxyRequest(String apiId, Map<String, Object> params) {
        ApiProxyConfig.ApiConfig apiConfig = apiProxyConfig.getApis().stream()
                .filter(api -> api.getApiId().equals(apiId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("API not found"));

        // 构建目标URL
        String targetUrl = buildTargetUrl(apiConfig, params);

        RequestBody requestBody = buildTargetBody(apiConfig, params);

//      构建请求体
        final Request.Builder requestBuilder = new Request.Builder()
                .url(targetUrl)
                .method(StringUtils.upperCase(apiConfig.getMethod()), requestBody);// 小写转大写


        // 添加请求头
        if (ObjectUtils.isNotEmpty(apiConfig.getHeaders())) {
            apiConfig.getHeaders().forEach(header -> {
                if (StringUtils.equalsIgnoreCase(header.getType(), HeaderTypeConstant.CONSTANT)) {
                    requestBuilder.addHeader(header.getName(), header.getValue());
                }
            });
        }


        final Request request = requestBuilder.build();

        // 执行请求
        try (Response response = okHttpClient.newCall(request).execute()) {
            final String body = response.body().string();
            Map<String, Object> responseMap = gson.fromJson(
                    body,
                    new TypeToken<Map<String, Object>>() {
                    }.getType()
            );
            return responseMap;
        } catch (IOException e) {
            throw new RuntimeException("API请求失败", e);
        }
    }

    private RequestBody buildTargetBody(ApiProxyConfig.ApiConfig apiConfig, Map<String, Object> params) {
//      todo 构建请求体
        return null;
    }


    /**
     * 构建请求url
     *
     * @param apiConfig
     * @param params
     * @return
     */
    private String buildTargetUrl(ApiProxyConfig.ApiConfig apiConfig, Map<String, Object> params) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(apiConfig.getTargetUrl()).newBuilder();

        apiConfig.getParams().forEach(param -> {
            if (StringUtils.equalsIgnoreCase(param.getType(), ParamTypeConstant.CONSTANT)) {
                urlBuilder.addQueryParameter(param.getName(), param.getValue());
            } else if (StringUtils.equalsIgnoreCase(param.getType(), ParamTypeConstant.VARIABLE)) {
                if (params.containsKey(param.getName())) {
                    urlBuilder.addQueryParameter(param.getName(), String.valueOf(params.get(param.getName())));
                }
            }
        });

        return urlBuilder.build().toString();
    }


}

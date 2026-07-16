package com.kuafu.api.controller;


import com.kuafu.api.domain.ApiProxyRequest;
import com.kuafu.api.service.ApiProxyService;
import com.kuafu.common.annotation.Log;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/apiProxy")
public class ApiProxyController {

    @Resource
    private ApiProxyService apiProxyService;

    /**
     * 三方api的请求代理接口
     *
     * @param apiProxyRequest
     * @return
     */
    @PostMapping
    @Log
    public BaseResponse<Map<String, Object>> proxyRequest(@RequestBody ApiProxyRequest apiProxyRequest) {
        final Map<String, Object> stringObjectMap = apiProxyService.proxyRequest(apiProxyRequest.getApiId(), apiProxyRequest.getParams());
        return ResultUtils.success(stringObjectMap);
    }
}

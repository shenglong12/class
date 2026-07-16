package com.kuafu.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiProxyServiceTest {

    @Resource
    private ApiProxyService apiProxyService;

    @Test
    void init() {
        apiProxyService.init();
    }

    @Test
    void proxyRequest() {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("location", "39.984059,116.307526");
        final Map<String, Object> stringObjectMap = apiProxyService.proxyRequest("geocoder", params);

        System.out.println(stringObjectMap);
    }
}
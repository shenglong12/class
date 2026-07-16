package com.kuafu.web.service;

import com.kuafu.MisAppBackendApplication;
import com.kuafu.common.cache.Cache;
import com.kuafu.common.component.RequestDataContext;
import com.kuafu.common.wx.WxAppAccessTokenRequest;
import com.kuafu.common.wx.WxAppCode2SessionRequest;
import com.kuafu.login.service.WxAppService;
//import com.yomahub.liteflow.builder.el.ELBus;
//import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
//import com.yomahub.liteflow.core.FlowExecutor;
//import com.yomahub.liteflow.flow.LiteflowResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = {MisAppBackendApplication.class})
@RunWith(SpringRunner.class)
class ConfigTest {


    @Resource
    private Cache redisCache;

    @Autowired
    private WxAppService appService;

//    @Resource
//    private FlowExecutor flowExecutor;


    /**
     * 测试缓存是否正常
     */
    @Test
    public void cacheTest() {
        redisCache.setCacheObject("111", "3333");
        Object cacheObject = redisCache.getCacheObject("111");
        System.out.println(cacheObject.toString());
    }

    @Test
    public void test1() {
        WxAppAccessTokenRequest request = WxAppAccessTokenRequest.builder().build();
        System.out.println(request);

        WxAppCode2SessionRequest request11 = WxAppCode2SessionRequest.builder().build();
        System.out.println(request11);
    }

    @Test
    public void test2() {
        appService.getPhone("111");
    }

//    @Test
//    public void test3() throws ClassNotFoundException {
//
//
////        Class<?> clazz = Class.forName("com.kuafu.table.domain.Partner");
////        QueryWrapper<?> queryWrapper = createQueryWrapper(clazz);
//        String value = "{\n" +
//                "                \"table\": \"Partner\",\n" +
//                "                \"where\": {\n" +
//                "                    \"_and\":[\n" +
//                "                        {\n" +
//                "                            \"op\": \"_eq\",\n" +
//                "                            \"key\": {\n" +
//                "                                \"name\": \"id\",\n" +
//                "                                \"type\": \"INT\"\n" +
//                "                            },\n" +
//                "                            \"value\":{\n" +
//                "                                \"kind\": \"literal\",\n" +
//                "                                \"value\": \"10\"\n" +
//                "                            }\n" +
//                "                        }\n" +
//                "                    ]\n" +
//                "                }\n" +
//                "              }";
//
//        String el = ELBus.then(ELBus.node("oneTableCmp").data("oneTableCmp", value)).toEL();
//
//        LiteFlowChainELBuilder.createChain().setChainId("test1").setEL(el).build();
//
//        LiteflowResponse response = flowExecutor.execute2Resp("test1", null, RequestDataContext.class);
////        queryWrapper.and(i->i.eq(""))
//        if (response.isSuccess()) {
//            RequestDataContext responseData = response.getFirstContextBean();
//            System.out.println(responseData.getResult());
//        } else {
//            Exception exception = response.getCause();
//            System.out.println(exception);
//        }
//        System.out.println(response);
//    }


}
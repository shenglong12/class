package com.kuafu.pay.business;

import com.kuafu.common.dynamic_config.config.DynamicRefreshScope;
import com.kuafu.common.util.SpringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class GeneralOrderServiceTest {

    @Resource
    private GeneralOrderBusinessService generalOrderBusinessService;
    @Resource
    private TestBeanConfig testBeanConfig;

    @Resource
    private DynamicRefreshScope dynamicRefreshScope;

    @Resource
    private ObjectProvider<TestBeanConfig> objectProvider;


    @Test
    void getUniqueOrderNo() {
//        final String uniqueOrderNo = generalOrderBusinessService.getUniqueOrderNo("1", "1");
//        final String uniqueOrderNo2 = generalOrderBusinessService.getUniqueOrderNo("1", "1");
//        final String uniqueOrderNo3 = generalOrderBusinessService.getUniqueOrderNo("1", "1");

//
//        System.out.println(uniqueOrderNo);
//        System.out.println(uniqueOrderNo2);
//        System.out.println(uniqueOrderNo3);
    }

    @Test
    void test_dunamic_refersh() {

//        System.out.println(objectProvider.getObject().hashCode());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());

        dynamicRefreshScope.refreshAll();
//        System.out.println(objectProvider.getObject().hashCode());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());
        System.out.println(objectProvider.getObject().getName());


    }

    @Test
    void test_dunamic_refersh2() {

//        System.out.println(objectProvider.getObject().hashCode());
        TestBeanConfig object = SpringUtils.getBean(TestBeanConfig.class);
        System.out.println(object.getName());
        System.out.println(object.getName());
        System.out.println(object.getName());
        System.out.println(object.getName());
        System.out.println(object.getName());
        System.out.println(object.getName());
        System.out.println(object.getName());

        dynamicRefreshScope.refreshAll();
        object = SpringUtils.getBean(TestBeanConfig.class);
//        System.out.println(objectProvider.getObject().hashCode());
        System.out.println(object.getName());
        System.out.println(object.getName());
        System.out.println(object.getName());
        System.out.println(object.getName());
        System.out.println(object.getName());
        System.out.println(object.getName());
        System.out.println(object.getName());


    }
}
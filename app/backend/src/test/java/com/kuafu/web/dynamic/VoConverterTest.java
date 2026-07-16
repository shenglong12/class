//package com.kuafu.web.dynamic;
//
//import com.kuafu.web.vo.OrderInfoAllPageVO;
//import com.kuafu.web.vo.OrderInfoPageVO;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//
//import java.util.HashMap;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class VoConverterTest {
//
//
//
////    @Test
//    static void convertPageVo() throws Exception {
//        final HashMap<String, Object> data = new HashMap<>();
//        data.put("current", 2);
//        data.put("pageSize", 2);
//         OrderInfoAllPageVO orderInfoAll =(OrderInfoAllPageVO) VoConverter.convertPageVo("order_info_all", data);
//        System.out.println(orderInfoAll.getCurrent());
//        System.out.println(orderInfoAll.getPageSize());
//        System.out.println(orderInfoAll);
//    }
//     public static void main(String[] args) throws Exception {
//        convertPageVo();
//    }
//}
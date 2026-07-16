package com.kuafu.web.dynamic;

import com.kuafu.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
public class DynamicServiceInvoker {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * @param tableName
     * @param vo
     * @param methodName page/list/add/update/get/delete
     * @throws Exception
     */
    public Object invoke(String tableName, Object vo, String methodName) throws Exception {
        // 尝试 ControllerService 模式（应用端使用）
        String serviceBeanName = toCamelCase(tableName) + "ControllerService";
        
        if (applicationContext.containsBean(serviceBeanName)) {
            Object service = applicationContext.getBean(serviceBeanName);
            Method method = findMethod(service.getClass(), methodName, vo.getClass());
            if (method != null) {
                return method.invoke(service, vo);
            }
        }
        
        // 如果找不到 ControllerService，尝试直接使用 Controller 和反射调用
        // 但管理端的Controller是@RestController，不能这样调用，所以直接返回错误
        throw new RuntimeException("找不到 Service Bean：" + serviceBeanName);
    }

    private Method findMethod(Class<?> clazz, String methodName, Class<?> paramType) {
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(methodName) &&
                    m.getParameterCount() == 1 &&
                    m.getParameterTypes()[0].isAssignableFrom(paramType)) {
                return m;
            }
        }
        return null;
    }

    private String toCamelCase(String tableName) {
        String entityName = VoConverter.tableName2Entity(tableName);
        return StringUtils.dbStrToHumpUpper(entityName);
    }
}


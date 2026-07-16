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
        String serviceBeanName = toCamelCase(tableName) + "ControllerService";

        if (!applicationContext.containsBean(serviceBeanName)) {
            throw new RuntimeException("找不到 Service Bean：" + serviceBeanName);
        }

        Object service = applicationContext.getBean(serviceBeanName);
        Method method = findMethod(service.getClass(), methodName, vo.getClass());
        if (method == null) {
            throw new RuntimeException("找不到方法：" + methodName + "(" + vo.getClass().getSimpleName() + ")");
        }

        return method.invoke(service, vo);
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

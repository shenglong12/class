package com.kuafu.login.service;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.login.IControllerService;
import com.kuafu.common.login.LoginUser;
import com.kuafu.common.login.SecurityUtils;
import com.kuafu.common.util.SpringUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.common.util.WrapperFactory;
import com.kuafu.login.config.LoginRelevanceConfig;
import com.kuafu.web.annotation.IsNotNullField;
import com.kuafu.web.handler.CustomTenantHandler;
import com.kuafu.web.handler.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "login", name = "enable")
public class LoginBusinessService {

    @Value("${login.table}")
    private String login_table;

    // 电话
    @Value("${login.select.table:table}")
    private String select_table;
    @Value("${login.select.column:phone_number}")
    private String select_table_column;

    // openid
    @Value("${login.openid.table:table}")
    private String openid_table;
    @Value("${login.openid.column:wx_open_id}")
    private String openid_table_column;

    @Value("${login.relevance_column_name:relevance_table}")
    private String relevance_table;

    @Value("${login.relevance_id_name:relevance_id}")
    private String relevance_id_name;

    public Object getCurrentUser() {

        final LoginUser loginUser = SecurityUtils.getLoginUser();
        final String relevanceTable = loginUser.getRelevanceTable();

        String pascalCase = StringUtils.dbStrToHumpUpper(relevanceTable);
        String pascalAll = pascalCase + "AllControllerService";

        if (SpringUtils.containsBean(pascalAll)) {
            IControllerService iService = SpringUtils.getBean(pascalAll);
            return iService.getById(loginUser.getRelevanceId());
        } else {
            IService iService = SpringUtils.getBean(pascalCase);
            return iService.getById(loginUser.getRelevanceId());
        }
    }

    /**
     * 查询对象
     *
     * @param key
     * @return
     */
    public Object getUserBySelectKey(Object key) {
        String table = login_table;
        if (StringUtils.isNotEmpty(select_table)) {
            table = select_table;
        }
        return getUser(table, select_table_column, key);
    }

    /**
     * 查询对象的ID
     *
     * @param key
     * @return
     */
    public Long getUserIdBySelectKey(Object key) {
        String table = login_table;
        if (StringUtils.isNotEmpty(select_table)) {
            table = select_table;
        }
        return getId(getUser(table, select_table_column, key));
    }

    public Long getUserIdByOpenId(Object key) {
        String table = login_table;
        if (StringUtils.isNotEmpty(openid_table)) {
            table = openid_table;
        }
        return getId(getUser(table, openid_table_column, key));
    }

    public void updateOpenIdById(Object userId, Object openId) {
        String table = login_table;
        if (StringUtils.isNotEmpty(openid_table)) {
            table = openid_table;
        }
        IService iService = SpringUtils.getBean(table);
        UpdateWrapper<?> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("login_id", userId)
                .set(openid_table_column, openId);
        iService.update(updateWrapper);
    }

    public void updateOpenIdByRelevanceId(Object relevanceId, Object openId) {
        String table = login_table;
        if (StringUtils.isNotEmpty(openid_table)) {
            table = openid_table;
        }
        IService iService = SpringUtils.getBean(table);
        UpdateWrapper<?> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq(relevance_id_name, relevanceId)
                .set(openid_table_column, openId);
        iService.update(updateWrapper);
    }

    public Long createNewUser(Object openId) {
        String table = login_table;
        String fieldName = StringUtils.dbStrToHumpLower(openid_table_column);
        Object entityObject = createNewUser(table);
        setFieldValue(entityObject, fieldName, openId);
        IService iService = SpringUtils.getBean(table);
        boolean flag = iService.save(entityObject);
        if (flag) {
            return getId(entityObject);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取对象 passwd
     *
     * @param obj
     * @return
     */
    public String password(Object obj) {
        Object value = WrapperFactory.getPassword(obj, "");
        if (value != null) {
            return value.toString();
        } else {
            return "";
        }
    }

    /**
     * 获取 对象的 ID
     *
     * @param obj
     * @return
     */
    public Long getId(Object obj) {
        if (obj != null) {
            Object id = WrapperFactory.getTableModelId(obj);
            if (id instanceof Integer) {
                return ((Integer) id).longValue();
            } else {
                return (Long) id;
            }
        } else {
            return null;
        }
    }


    private Object getUser(String table, String key, Object value) {
        IService iService = SpringUtils.getBean(table);
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(key, value)
                .eq(relevance_table, StringUtils.dbStrToHumpLower(LoginRelevanceConfig.getLoginRelevanceTable()))
                .isNotNull(relevance_id_name)
                .ne(relevance_id_name, "");
        return iService.getOne(queryWrapper);
    }

    /**
     * 创建一个新用户
     *
     * @param table t
     * @return o
     */
    private Object createNewUser(String table) {
        Class<?> clazz = SpringUtils.getType(table);
        String className = clazz.getName();
        if (StringUtils.containsAnyIgnoreCase(className, "$")) {
            // 说明是代理
            clazz = clazz.getSuperclass();
        }

        Type superClass = clazz.getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Type entityType = actualTypeArguments[1];

            try {
                Class<?> entityClazz = Class.forName(entityType.getTypeName());
                return entityClazz.getDeclaredConstructor().newInstance();
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    private void setFieldValue(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // 设置可访问
            field.set(obj, value); // 设置字段值
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public Object getValue(Object current, String fileName) {
        final Class<?> aClass = current.getClass();
        final Field[] declaredFields = aClass.getDeclaredFields();
        try {
            for (Field f : declaredFields) {
                f.setAccessible(true);
                final String name = f.getName();
                if (StringUtils.equalsIgnoreCase(name, fileName)) {
                    return f.get(current);
                }
            }
            return null;
        } catch (IllegalAccessException e) {

            throw new BusinessException(ErrorCode.PARAMS_ERROR, "关联字段不存在");
        }
    }

    /**
     * @param phone          手机号
     * @param relevanceTable 登录关联表的名称
     * @return ID
     */
    public Long insertRelevanceInfo(String phone, String relevanceTable) {
        // 驼峰转下划线
        String table = StringUtils.toUnderScoreCase(relevanceTable);
        // 下划线转 entityName
        String entityName = StringUtils.dbStrToHumpUpper(table);
        Object entityObject = createNewUser(entityName);

        String fieldName = StringUtils.dbStrToHumpLower(select_table_column);
        // 拿到所有非空字段，设置默认值
        Class<?> clazz = entityObject.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            // 检查字段是否有@IsNotNullField注解
            if (field.isAnnotationPresent(IsNotNullField.class)) {
                try {
                    // 设置字段为可访问的
                    field.setAccessible(true);

                    // 检查字段是否已经设置了值
                    if (field.get(entityObject) == null) {
                        // 根据字段类型设置默认值
                        if (field.getType() == String.class) {
                            field.set(entityObject, "");
                        } else if (field.getType() == Integer.class) {
                            field.set(entityObject, 0);
                        } else if (field.getType() == Date.class) {
                            field.set(entityObject, new Date());
                        }
                    }
                } catch (IllegalAccessException e) {
                    // 处理异常
                    
                }
            }
        }

        // 检查数据是否已经存在
        IService iService = SpringUtils.getBean(entityName);
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", phone);
        Object object = iService.getOne(queryWrapper);
        if (object != null) {
            return getId(object);
        }

        setFieldValue(entityObject, fieldName, phone);
        boolean flag = iService.save(entityObject);
        if (flag) {
            return getRelevanceIdByPhoneAndSave(phone, relevanceTable, String.valueOf(getId(entityObject)));
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 根据手机号获取loginId
     *
     * @param phone          手机号
     * @param relevanceTable 关联表
     * @param relevanceId    关联ID
     * @return
     */
    public long getRelevanceIdByPhoneAndSave(String phone, String relevanceTable, String relevanceId) {
        String entityName = StringUtils.dbStrToHumpUpper(login_table);
        Object loginInfo = createNewUser(entityName);

        // 设置字段值
        Map<String, Object> fieldMap = new HashMap<>();
        String fieldPhoneNumber = StringUtils.dbStrToHumpLower(select_table_column);
        fieldMap.put(fieldPhoneNumber, phone);
        String fieldRelevanceTable = StringUtils.dbStrToHumpLower(relevance_table);
        fieldMap.put(fieldRelevanceTable, relevanceTable);
        String fieldRelevanceId = StringUtils.dbStrToHumpLower(relevance_id_name);
        fieldMap.put(fieldRelevanceId, relevanceId);

        for (String key : fieldMap.keySet()) {
            Object value = fieldMap.get(key);
            setFieldValue(loginInfo, key, value);
        }
        // 插入登录信息
        IService iService = SpringUtils.getBean(entityName);
        boolean result = iService.save(loginInfo);
        if (result) {
            return getId(loginInfo);
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
    }

    /**
     * 获取关联登录表用户的ID
     *
     * @param phone
     * @return
     */
    public Long getUserIdByRelevanceTable(String relevanceTable, String phone) {
        // 驼峰转下划线
        String table = StringUtils.toUnderScoreCase(relevanceTable);
        // 下划线转 entityName
        String entityName = StringUtils.dbStrToHumpUpper(table);

        // 检查数据是否已经存在
        IService iService = SpringUtils.getBean(entityName);
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", phone);
        // 查询时忽略 tenant_id
        if (TenantContextHolder.getEnableTenant()) {
            CustomTenantHandler.threadLocalSet.get().add(table);
        }
        Object object = iService.getOne(queryWrapper);
        CustomTenantHandler.threadLocalSet.get().remove(table);
        if (object != null) {
            return getId(object);
        }
        return null;
    }

    /**
     * 获取登录用户的 tenantId
     *
     * @return
     */
    public Integer getUserTenantIdByLoginUser(LoginUser loginUser) {
        // 通过关联用户的ID，查询关联表信息的租户ID
        String relevanceId = loginUser.getRelevanceId();
        String relevanceTable = loginUser.getRelevanceTable();
        String relevanceTableScore = StringUtils.toUnderScoreCase(relevanceTable);
        String RelevanceTableUpper = StringUtils.dbStrToHumpUpper(relevanceTableScore);
        Object entityObject = this.createNewUser(RelevanceTableUpper);
        Class<?> clazz = entityObject.getClass();
        String relevanceUserIdName = "";
        for (Field field : clazz.getDeclaredFields()) {
            // 检查字段是否有@TableId注解
            if (field.isAnnotationPresent(TableId.class)) {
                // 设置字段为可访问的
                field.setAccessible(true);
                relevanceUserIdName = field.getName();
                break;
            }
        }

        IService iservice = SpringUtils.getBean(RelevanceTableUpper);
        QueryWrapper<Object> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq(StringUtils.toUnderScoreCase(relevanceUserIdName), relevanceId);

        // 首次登录忽略
        CustomTenantHandler.threadLocalSet.get().add(relevanceTableScore);
        Object bean = iservice.getOne(objectQueryWrapper);
        CustomTenantHandler.threadLocalSet.get().remove(relevanceTableScore);

        Object tenantId = this.getValue(bean, StringUtils.dbStrToHumpLower(TenantContextHolder.TENANT_TABLE_FIELD_NAME));
        log.info("获取登录用户的 tenantId:{}", tenantId);
        if (tenantId == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR.getCode(), "还没有绑定企业");
        }
        loginUser.setTenantId(Integer.valueOf(tenantId.toString()));
        return Integer.valueOf(tenantId.toString());
    }
}

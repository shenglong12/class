package com.kuafu.web.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.kuafu.common.login.SecurityUtils;
import com.kuafu.web.entity.Login;
import com.kuafu.web.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MyEventListener {


    private final Map<String, List<LoginTableVo>> loginMap = EventConstant.loginTableVoList.stream()
            .collect(Collectors.groupingBy(LoginTableVo::getName));

    private final EventBus eventBus;

    private final ILoginService loginService;

    @Autowired
    public MyEventListener(EventBus eventBus, ILoginService loginService) {
        this.eventBus = eventBus;
        this.loginService = loginService;
    }

    @PostConstruct
    public void init() {
        //注册订阅者
        eventBus.register(this);
    }

    @Subscribe
    public void handleEvent(EventVo event) {
        log.info("收到消息:{}", event.toString());
        final String model = event.getModel();
        final String tableName = event.getTableName();
        if (!loginMap.containsKey(tableName)) {
            return;
        }
//      获取login对象
        Login login = getLoginVo(event.getData(), loginMap.get(tableName).get(0));

        if (StringUtils.equalsIgnoreCase(model, "add")) {
            loginService.save(login);
        } else if (StringUtils.equalsIgnoreCase(model, "update")) {
//          更新逻辑一样，表一样,只更新用户名与密码
            LambdaUpdateWrapper<Login> updateWrapper = new LambdaUpdateWrapper<>();
            if (StringUtils.isNotEmpty(login.getPhoneNumber())) {
                updateWrapper.set(Login::getPhoneNumber, login.getPhoneNumber());
            }
            if (StringUtils.isNotEmpty(login.getPassword())) {
                updateWrapper.set(Login::getPassword, login.getPassword());
            }


            updateWrapper.eq(Login::getRelevanceTable, tableName)
                    .eq(Login::getRelevanceId, login.getRelevanceId());
            final boolean update = loginService.update(updateWrapper);
            if (!update) {
                loginService.save(login);
            }
        } else if (StringUtils.equalsIgnoreCase(model, "delete")) {
            final LambdaQueryWrapper<Login> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Login::getRelevanceTable, tableName)
                    .eq(Login::getRelevanceId, login.getRelevanceId());

            loginService.remove(wrapper);
        }
    }

    private Login getLoginVo(Object data, LoginTableVo loginTableVo) {
        final String passwordKey = loginTableVo.getPasswordKey();
        final String primaryKey = loginTableVo.getPrimaryKey();
        final String userNameKey = loginTableVo.getUserNameKey();
        // TODO: 2024/9/19 什么是否使用手机号，什么时候使用 用户名密码
        final String valueByFiledName = getValueByFiledName(data, userNameKey);
        String valueByFiledNamePassword = getValueByFiledName(data, passwordKey);
        final String valueByFiledNameRelevanceId = getValueByFiledName(data, primaryKey);
        if (StringUtils.isEmpty(valueByFiledNamePassword)) {
            valueByFiledNamePassword = "123456";
        }

        return Login.builder().phoneNumber(valueByFiledName)
                .password(SecurityUtils.encryptPassword(valueByFiledNamePassword))
                .relevanceId(valueByFiledNameRelevanceId)
                .relevanceTable(loginTableVo.getName())
                .build();

    }

    /**
     * f
     *
     * @param data
     * @param userNameKey
     * @return
     */
    private String getValueByFiledName(Object data, String userNameKey) {
        if (StringUtils.isEmpty(userNameKey)) {
            return null;
        }
        final Class<?> aClass = data.getClass();
        try {
            final Field field = aClass.getDeclaredField(userNameKey);
            field.setAccessible(true);
            final Object o = field.get(data);
            if (o != null) {
                return o.toString();
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
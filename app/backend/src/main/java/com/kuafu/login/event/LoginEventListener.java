package com.kuafu.login.event;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.kuafu.common.event.EventVo;
import com.kuafu.common.login.SecurityUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.common.util.WrapperFactory;
import com.kuafu.login.domain.Login;
import com.kuafu.login.domain.LoginTableReverence;
import com.kuafu.login.service.ILoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginEventListener {
    private final EventBus eventBus;
    private final ILoginService loginService;

    public final List<String> passwordKeys = Arrays.asList("passwd", "password", "pwd", "pass", "pword", "pws", "pswd");
    public final List<String> usernameKeys = Arrays.asList("username", "userName", "user_name", "usrname", "uname", "accountName", "acctName");
    public final List<String> phoneKeys = Arrays.asList(
            "phone", "phone_number", "phonenumber", "phoneNumber", "mobile",
            "mobile_number", "mobileNumber", "tel", "tel_number",
            "telephone", "telephone_number"
    );


    @PostConstruct
    public void init() {
        //注册订阅者
        eventBus.register(this);
    }

    @Subscribe
    public void handleEvent(EventVo event) {
        log.info("============={}", event);

        String table = event.getTableName();
        if (LoginTableReverence.loginMap().containsKey(table)) {

            String model = event.getModel();
            Login login = convert(event.getData());
            if (StringUtils.equalsIgnoreCase(model, "add") && login != null) {
                login.setRelevanceTable(StringUtils.dbStrToHumpLower(event.getTableName()));
                loginService.save(login);
            }

        }
    }

    private Login convert(Object data) {
        Login login = new Login();

        login.setRelevanceId(String.valueOf(WrapperFactory.getTableModelId(data)));
        Object passwd = WrapperFactory.getValueByKeys(data, passwordKeys);
        if (Objects.nonNull(passwd)) {
            login.setPassword(SecurityUtils.encryptPassword(String.valueOf(passwd)));
        } else {
            //默认密码
            login.setPassword(SecurityUtils.encryptPassword("1234456"));
        }
        Object userName = WrapperFactory.getValueByKeys(data, usernameKeys);
        if (Objects.nonNull(userName)) {
            login.setUserName(String.valueOf(userName));
            login.setPhoneNumber(String.valueOf(userName));
        } else {
            Object phone = WrapperFactory.getValueByKeys(data, phoneKeys);
            if (Objects.nonNull(phone)) {
                login.setUserName(String.valueOf(phone));
                login.setPhoneNumber(String.valueOf(phone));
            } else {
                return null;
            }
        }

        return login;
    }
}

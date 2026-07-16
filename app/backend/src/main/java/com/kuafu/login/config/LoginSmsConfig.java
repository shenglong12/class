package com.kuafu.login.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Component
@ConfigurationProperties(prefix = "login.sms")
public class LoginSmsConfig {
    private int codeTimeout;

    private boolean debug;

    public void setCodeTimeout(int codeTimeout) {
        this.codeTimeout = codeTimeout;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}

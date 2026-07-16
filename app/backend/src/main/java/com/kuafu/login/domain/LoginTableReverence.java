package com.kuafu.login.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum LoginTableReverence {

            user_info("user_info","user_info"),
    ;


    private String label;
    private String value;

    LoginTableReverence(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static Map<String, String> loginMap() {
        final LoginReverence[] values = LoginReverence.values();
        return Arrays.stream(LoginTableReverence.values())
                .collect(Collectors.toMap(LoginTableReverence::getValue, LoginTableReverence::getLabel));
    }
}

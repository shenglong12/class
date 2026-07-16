package com.kuafu.web.controller;

import com.kuafu.web.entity.SelectVO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum LoginReverence {

userInfo("用户信息","userInfo"),;
    private String label;
    private String value;

    LoginReverence(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static List<SelectVO> all() {
        final LoginReverence[] values = LoginReverence.values();
        return Arrays.stream(values).map(r -> {
            return new SelectVO(r.getValue(), r.getLabel());
        }).collect(Collectors.toList());
    }
}

package com.kuafu.login.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SelectVo<T> {
    private T value;
    private String label;
    private String text;
}

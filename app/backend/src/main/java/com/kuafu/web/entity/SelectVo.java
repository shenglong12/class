package com.kuafu.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SelectVo<T> {
    private T value;
    private String text;
}

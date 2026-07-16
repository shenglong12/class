package com.kuafu.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class Filter {
    private String name;

    /**
     * 小于
     */
    private Object lt;

    /**
     * 大于
     */
    private Object gt;

    /**
     * 大于小于
     */
    private Object ge;

    private Object le;

    private String like;

    private String leftLike;

    private String rightLike;

    private Object eq;

    private Object ne;

    private List<String> in;

    private List<String> notIn;


    private Boolean isNull;

    private Boolean isNotNull;

}

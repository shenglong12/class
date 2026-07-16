package com.kuafu.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SortEnum {
    DESC("desc", "降序"),
    ASC("asc", "升序");


    String name;
    String value;

    SortEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


    /**
     * 构建枚举值
     * @param value
     * @return
     */
    @JsonCreator
    public static SortEnum fromValue(String value) {
        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.value.equalsIgnoreCase(value)) {
                return sortEnum;
            }
        }

        return ASC;

    }


}

package com.kuafu.login.domain;

import com.kuafu.login.domain.SelectVo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum LoginReverence {

            kfSystemConfig("系统配置","kfSystemConfig"),
        roleEnum("角色枚举","roleEnum"),
        userStatusEnum("用户状态","userStatusEnum"),
        buildingEnum("教学楼枚举","buildingEnum"),
        userInfo("用户信息","userInfo"),
        classroomInfo("教室信息","classroomInfo"),
        courseInfo("课程信息","courseInfo"),
        repairStatusEnum("报修状态","repairStatusEnum"),
        repairOrder("报修单","repairOrder"),
        bookingStatusEnum("预约状态","bookingStatusEnum"),
        bookingInfo("预约信息","bookingInfo"),
        backgroundImage("背景图片","backgroundImage"),
        scanRecord("扫码记录","scanRecord"),
    ;


    private String label;
    private String value;

    LoginReverence(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public static List<SelectVo> all() {
        final LoginReverence[] values = LoginReverence.values();
        return Arrays.stream(values).map(r -> {
            return new SelectVo(r.getValue(), r.getLabel(),r.getLabel());
        }).collect(Collectors.toList());
    }
}

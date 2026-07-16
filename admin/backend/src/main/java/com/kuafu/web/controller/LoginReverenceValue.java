package com.kuafu.web.controller;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum LoginReverenceValue {
userInfo("userInfo","scan_record_all.get_user_info_list"),roleEnum("roleEnum","user_info_all.get_role_enum_list"),userStatusEnum("userStatusEnum","user_info_all.get_user_status_enum_list"),classroomInfo("classroomInfo","booking_info_all.get_classroom_info_list"),bookingStatusEnum("bookingStatusEnum","booking_info_all.get_booking_status_enum_list"),repairStatusEnum("repairStatusEnum","repair_order_all.get_repair_status_enum_list"),buildingEnum("buildingEnum","classroom_info_all.get_building_enum_list"),user_status_enum("user_status_enum","user_status_enum.get_select_list"),booking_info("booking_info","booking_info.get_select_list"),dynamic_api_setting("dynamic_api_setting","dynamic_api_setting.get_select_list"),repair_order("repair_order","repair_order.get_select_list"),role_enum("role_enum","role_enum.get_select_list"),classroom_info("classroom_info","classroom_info.get_select_list"),booking_status_enum("booking_status_enum","booking_status_enum.get_select_list"),background_image("background_image","background_image.get_select_list"),user_info("user_info","user_info.get_select_list"),kf_system_config("kf_system_config","kf_system_config.get_select_list"),course_info("course_info","course_info.get_select_list"),repair_status_enum("repair_status_enum","repair_status_enum.get_select_list"),scan_record("scan_record","scan_record.get_select_list"),building_enum("building_enum","building_enum.get_select_list"),;
    private String label;
    private String value;


    LoginReverenceValue(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static String getValue(String label) {
        for (LoginReverenceValue item : values()) {
            if (StringUtils.equalsIgnoreCase(item.getLabel(), label)) {
                return item.getValue();
            }
        }
        return null;
    }
}

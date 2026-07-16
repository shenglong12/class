package com.kuafu.web.entity;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import com.kuafu.common.entity.StaticResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * <p>  预约信息 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfoAll {
    @TableField(value="ui.college")
    @JsonProperty("college")
    private String college;
    @TableField(value="bi.user_info_user_info_id_1")
    @JsonProperty("user_info_user_info_id_1")
    private Integer userInfoUserInfoId1;
    @TableField(value="bi.create_time")
    @JsonProperty("create_time")
    private Date createTime;
    @TableField(value="bi.classroom_info_classroom_info_id_1")
    @JsonProperty("classroom_info_classroom_info_id_1")
    private Integer classroomInfoClassroomInfoId1;
    @TableField(value="bi.purpose")
    @JsonProperty("purpose")
    private String purpose;
    @TableField(value="bi.booking_date")
    @JsonProperty("booking_date")
    private Date bookingDate;
    @TableField(value="bse.status_name")
    @JsonProperty("status_name")
    private String statusName;
    @TableField(value="bi.booking_info_id")
    @JsonProperty("booking_info_id")
    private Integer bookingInfoId;
    @TableField(value="bi.end_time")
    @JsonProperty("end_time")
    private String endTime;
    @TableField(value="ui.student_id")
    @JsonProperty("student_id")
    private String studentId;
    @TableField(value="ui.role_enum_role_enum_id_1")
    @JsonProperty("role_enum_role_enum_id_1")
    private Integer roleEnumRoleEnumId1;
    @TableField(value="ci.room_number")
    @JsonProperty("room_number")
    private String roomNumber;
    @TableField(value="ci.qr_code_path")
    @JsonProperty("qr_code_path")
    private String qrCodePath;
    @TableField(value="bi.start_time")
    @JsonProperty("start_time")
    private String startTime;
    @TableField(value="ui.password")
    @JsonProperty("password")
    private String password;
    @TableField(value="ci.building_enum_building_enum_id_1")
    @JsonProperty("building_enum_building_enum_id_1")
    private Integer buildingEnumBuildingEnumId1;
    @TableField(value="ui.registration_date")
    @JsonProperty("registration_date")
    private Date registrationDate;
    @TableField(value="ui.major")
    @JsonProperty("major")
    private String major;
    @TableField(value="ui.name")
    @JsonProperty("name")
    private String name;
    @TableField(value="bi.booking_status_enum_booking_status_enum_id_1")
    @JsonProperty("booking_status_enum_booking_status_enum_id_1")
    private Integer bookingStatusEnumBookingStatusEnumId1;
    @TableField(value="ui.user_status_enum_user_status_enum_id_1")
    @JsonProperty("user_status_enum_user_status_enum_id_1")
    private Integer userStatusEnumUserStatusEnumId1;
    @TableField(value="ui.phone_number")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @TableField(value="bi.time_period")
    @JsonProperty("time_period")
    private String timePeriod;










}

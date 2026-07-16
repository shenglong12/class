package com.kuafu.web.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuafu.common.annotation.Excel;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.kuafu.common.annotation.Excel.*;

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
public class BookingInfoAll  {
    @TableField(value="ui.college")
    @JsonProperty(value = "college")
    @Excel(name = "所属学院")
    private String college;
    @TableField(value="bi.user_info_user_info_id_1")
    @JsonProperty(value = "userInfoUserInfoId1")
    @Excel(name = "预约用户")
    private Integer userInfoUserInfoId1;
    @TableField(value="bi.create_time")
    @JsonProperty(value = "createTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "创建时间")
    private Date createTime;
    @TableField(value="bi.purpose")
    @JsonProperty(value = "purpose")
    @Excel(name = "预约用途")
    private String purpose;
    @TableField(value="ui.role_enum_role_enum_id_1")
    @JsonProperty(value = "roleEnumRoleEnumId1")
    @Excel(name = "角色")
    private Integer roleEnumRoleEnumId1;
    @TableField(value="ci.room_number")
    @JsonProperty(value = "roomNumber")
    @Excel(name = "教室编号")
    private String roomNumber;
    @TableField(value="ci.qr_code_path")
    @JsonProperty(value = "qrCodePath")
    @Excel(name = "二维码路径")
    private String qrCodePath;
    @TableField(value="bi.booking_info_id")
    @JsonProperty(value = "bookingInfoId")
    @Excel(name = "主键")
    private Integer bookingInfoId;
    @TableField(value="ui.phone_number")
    @JsonProperty(value = "phoneNumber")
    @Excel(name = "手机号")
    private String phoneNumber;
    @TableField(value="bi.end_time")
    @JsonProperty(value = "endTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "预约结束时间")
    private String endTime;
    @TableField(value="bse.status_name")
    @JsonProperty(value = "statusName")
    @Excel(name = "状态名称")
    private String statusName;
    @TableField(value="ui.registration_date")
    @JsonProperty(value = "registrationDate")
    @Excel(name = "注册时间")
    private Date registrationDate;
    @TableField(value="bi.booking_date")
    @JsonProperty(value = "bookingDate")
    @Excel(name = "预约日期")
    private Date bookingDate;
    @TableField(value="bi.start_time")
    @JsonProperty(value = "startTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "预约开始时间")
    private String startTime;
    @TableField(value="ui.user_status_enum_user_status_enum_id_1")
    @JsonProperty(value = "userStatusEnumUserStatusEnumId1")
    @Excel(name = "用户状态")
    private Integer userStatusEnumUserStatusEnumId1;
    @TableField(value="ui.student_id")
    @JsonProperty(value = "studentId")
    @Excel(name = "学号")
    private String studentId;
    @TableField(value="ui.password")
    @JsonProperty(value = "password")
    @Excel(name = "密码")
    private String password;
    @TableField(value="ui.major")
    @JsonProperty(value = "major")
    @Excel(name = "专业")
    private String major;
    @TableField(value="ci.building_enum_building_enum_id_1")
    @JsonProperty(value = "buildingEnumBuildingEnumId1")
    @Excel(name = "教学楼")
    private Integer buildingEnumBuildingEnumId1;
    @TableField(value="bi.booking_status_enum_booking_status_enum_id_1")
    @JsonProperty(value = "bookingStatusEnumBookingStatusEnumId1")
    @Excel(name = "预约状态")
    private Integer bookingStatusEnumBookingStatusEnumId1;
    @TableField(value="ui.name")
    @JsonProperty(value = "name")
    @Excel(name = "姓名")
    private String name;
    @TableField(value="bi.classroom_info_classroom_info_id_1")
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    @Excel(name = "教室")
    private Integer classroomInfoClassroomInfoId1;
    @TableField(value="bi.time_period")
    @JsonProperty(value = "timePeriod")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "时间段")
    private String timePeriod;

}

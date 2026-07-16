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
 * <p>  扫码记录 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScanRecordAll {
    @TableField(value="ui.college")
    @JsonProperty("college")
    private String college;
    @TableField(value="sr.building_name")
    @JsonProperty("building_name")
    private String buildingName;
    @TableField(value="sr.user_info_user_info_id_1")
    @JsonProperty("user_info_user_info_id_1")
    private Integer userInfoUserInfoId1;
    @TableField(value="sr.room_number")
    @JsonProperty("room_number")
    private String roomNumber;
    @TableField(value="ui.student_id")
    @JsonProperty("student_id")
    private String studentId;
    @TableField(value="ui.role_enum_role_enum_id_1")
    @JsonProperty("role_enum_role_enum_id_1")
    private Integer roleEnumRoleEnumId1;
    @TableField(value="ui.password")
    @JsonProperty("password")
    private String password;
    @TableField(value="ui.registration_date")
    @JsonProperty("registration_date")
    private Date registrationDate;
    @TableField(value="ui.major")
    @JsonProperty("major")
    private String major;
    @TableField(value="ui.name")
    @JsonProperty("name")
    private String name;
    @TableField(value="ui.user_status_enum_user_status_enum_id_1")
    @JsonProperty("user_status_enum_user_status_enum_id_1")
    private Integer userStatusEnumUserStatusEnumId1;
    @TableField(value="ui.phone_number")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @TableField(value="sr.scan_id")
    @JsonProperty("scan_id")
    private Integer scanId;
    @TableField(value="sr.scan_time")
    @JsonProperty("scan_time")
    private Date scanTime;










}

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
public class ScanRecordAll  {
    @TableField(value="ui.college")
    @JsonProperty(value = "college")
    @Excel(name = "所属学院")
    private String college;
    @TableField(value="sr.user_info_user_info_id_1")
    @JsonProperty(value = "userInfoUserInfoId1")
    @Excel(name = "用户ID")
    private Integer userInfoUserInfoId1;
    @TableField(value="sr.building_name")
    @JsonProperty(value = "buildingName")
    @Excel(name = "教学楼名称")
    private String buildingName;
    @TableField(value="ui.role_enum_role_enum_id_1")
    @JsonProperty(value = "roleEnumRoleEnumId1")
    @Excel(name = "角色")
    private Integer roleEnumRoleEnumId1;
    @TableField(value="sr.scan_id")
    @JsonProperty(value = "scanId")
    @Excel(name = "主键ID")
    private Integer scanId;
    @TableField(value="sr.room_number")
    @JsonProperty(value = "roomNumber")
    @Excel(name = "教室编号")
    private String roomNumber;
    @TableField(value="ui.phone_number")
    @JsonProperty(value = "phoneNumber")
    @Excel(name = "手机号")
    private String phoneNumber;
    @TableField(value="ui.registration_date")
    @JsonProperty(value = "registrationDate")
    @Excel(name = "注册时间")
    private Date registrationDate;
    @TableField(value="ui.user_status_enum_user_status_enum_id_1")
    @JsonProperty(value = "userStatusEnumUserStatusEnumId1")
    @Excel(name = "用户状态")
    private Integer userStatusEnumUserStatusEnumId1;
    @TableField(value="sr.scan_time")
    @JsonProperty(value = "scanTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "扫码时间")
    private Date scanTime;
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
    @TableField(value="ui.name")
    @JsonProperty(value = "name")
    @Excel(name = "姓名")
    private String name;

}

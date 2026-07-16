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
 * <p>  报修单 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairOrderAll {
    @TableField(value="ui.college")
    @JsonProperty("college")
    private String college;
    @TableField(value="ro.user_info_user_info_id_1")
    @JsonProperty("user_info_user_info_id_1")
    private Integer userInfoUserInfoId1;
    @TableField(value="ro.create_time")
    @JsonProperty("create_time")
    private Date createTime;
    @TableField(value="ro.classroom_info_classroom_info_id_1")
    @JsonProperty("classroom_info_classroom_info_id_1")
    private Integer classroomInfoClassroomInfoId1;
    @TableField(value="rse.status_name")
    @JsonProperty("status_name")
    private String statusName;
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
    @TableField(value="ro.content")
    @JsonProperty("content")
    private String content;
    @TableField(value="ro.repair_order_id")
    @JsonProperty("repair_order_id")
    private Integer repairOrderId;
    @TableField(value="ro.feedback")
    @JsonProperty("feedback")
    private String feedback;
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
    @TableField(value="ui.user_status_enum_user_status_enum_id_1")
    @JsonProperty("user_status_enum_user_status_enum_id_1")
    private Integer userStatusEnumUserStatusEnumId1;
    @TableField(value="ui.phone_number")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @TableField(value="ro.repair_status_enum_repair_status_enum_id_1")
    @JsonProperty("repair_status_enum_repair_status_enum_id_1")
    private Integer repairStatusEnumRepairStatusEnumId1;


@TableField(value="image_files")
@JsonProperty("image_files")
private List<StaticResource> imageFiles;








}

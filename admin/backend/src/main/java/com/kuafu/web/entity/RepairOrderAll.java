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
public class RepairOrderAll  {
    @TableField(value="ui.college")
    @JsonProperty(value = "college")
    @Excel(name = "所属学院")
    private String college;
    @TableField(value="ro.user_info_user_info_id_1")
    @JsonProperty(value = "userInfoUserInfoId1")
    @Excel(name = "报修用户")
    private Integer userInfoUserInfoId1;
    @TableField(value="ro.create_time")
    @JsonProperty(value = "createTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "创建时间")
    private Date createTime;
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
    @TableField(value="ui.phone_number")
    @JsonProperty(value = "phoneNumber")
    @Excel(name = "手机号")
    private String phoneNumber;
    @TableField(value="ro.repair_order_id")
    @JsonProperty(value = "repairOrderId")
    @Excel(name = "主键")
    private Integer repairOrderId;
    @TableField(value="rse.status_name")
    @JsonProperty(value = "statusName")
    @Excel(name = "状态名称")
    private String statusName;
    @TableField(value="ui.registration_date")
    @JsonProperty(value = "registrationDate")
    @Excel(name = "注册时间")
    private Date registrationDate;
    @TableField(value="ro.content")
    @JsonProperty(value = "content")
    @Excel(name = "报修内容")
    private String content;
    @TableField(value="ui.user_status_enum_user_status_enum_id_1")
    @JsonProperty(value = "userStatusEnumUserStatusEnumId1")
    @Excel(name = "用户状态")
    private Integer userStatusEnumUserStatusEnumId1;
    @TableField(value="ro.feedback")
    @JsonProperty(value = "feedback")
    @Excel(name = "处理反馈")
    private String feedback;
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
    @TableField(value="ro.image_files_resource_key")
    @JsonProperty(value = "imageFilesResourceKey")
    @Excel(cellType = ColumnType.IMAGE, name = "报修图片")
    private String imageFilesResourceKey;
    @TableField(value="ui.name")
    @JsonProperty(value = "name")
    @Excel(name = "姓名")
    private String name;
    @TableField(value="ro.repair_status_enum_repair_status_enum_id_1")
    @JsonProperty(value = "repairStatusEnumRepairStatusEnumId1")
    @Excel(name = "报修状态")
    private Integer repairStatusEnumRepairStatusEnumId1;
    @TableField(value="ro.classroom_info_classroom_info_id_1")
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    @Excel(name = "教室")
    private Integer classroomInfoClassroomInfoId1;

    @TableField(value="image_files")
    @JsonProperty("imageFiles")
    private List<StaticResource> imageFiles;
}

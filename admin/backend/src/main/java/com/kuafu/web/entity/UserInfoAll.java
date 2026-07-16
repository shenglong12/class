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
 * <p>  用户信息 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAll  {
    @TableField(value="ui.student_id")
    @JsonProperty(value = "studentId")
    @Excel(name = "学号")
    private String studentId;
    @TableField(value="ui.college")
    @JsonProperty(value = "college")
    @Excel(name = "所属学院")
    private String college;
    @TableField(value="ui.user_info_id")
    @JsonProperty(value = "userInfoId")
    @Excel(name = "主键")
    private Integer userInfoId;
    @TableField(value="ui.password")
    @JsonProperty(value = "password")
    @Excel(name = "密码")
    private String password;
    @TableField(value="ui.major")
    @JsonProperty(value = "major")
    @Excel(name = "专业")
    private String major;
    @TableField(value="ui.role_enum_role_enum_id_1")
    @JsonProperty(value = "roleEnumRoleEnumId1")
    @Excel(name = "角色")
    private Integer roleEnumRoleEnumId1;
    @TableField(value="re.role_name")
    @JsonProperty(value = "roleName")
    @Excel(name = "角色名称")
    private String roleName;
    @TableField(value="ui.name")
    @JsonProperty(value = "name")
    @Excel(name = "姓名")
    private String name;
    @TableField(value="ui.phone_number")
    @JsonProperty(value = "phoneNumber")
    @Excel(name = "手机号")
    private String phoneNumber;
    @TableField(value="use.status_name")
    @JsonProperty(value = "statusName")
    @Excel(name = "状态名称")
    private String statusName;
    @TableField(value="ui.registration_date")
    @JsonProperty(value = "registrationDate")
    @Excel(name = "注册时间")
    private Date registrationDate;
    @TableField(value="ui.user_status_enum_user_status_enum_id_1")
    @JsonProperty(value = "userStatusEnumUserStatusEnumId1")
    @Excel(name = "用户状态")
    private Integer userStatusEnumUserStatusEnumId1;

}

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
public class UserInfoAll {
    @TableField(value="ui.college")
    @JsonProperty("college")
    private String college;
    @TableField(value="re.role_name")
    @JsonProperty("role_name")
    private String roleName;
    @TableField(value="ui.password")
    @JsonProperty("password")
    private String password;
    @TableField(value="ui.registration_date")
    @JsonProperty("registration_date")
    private Date registrationDate;
    @TableField(value="ui.major")
    @JsonProperty("major")
    private String major;
    @TableField(value="use.status_name")
    @JsonProperty("status_name")
    private String statusName;
    @TableField(value="ui.name")
    @JsonProperty("name")
    private String name;
    @TableField(value="ui.student_id")
    @JsonProperty("student_id")
    private String studentId;
    @TableField(value="ui.role_enum_role_enum_id_1")
    @JsonProperty("role_enum_role_enum_id_1")
    private Integer roleEnumRoleEnumId1;
    @TableField(value="ui.user_status_enum_user_status_enum_id_1")
    @JsonProperty("user_status_enum_user_status_enum_id_1")
    private Integer userStatusEnumUserStatusEnumId1;
    @TableField(value="ui.phone_number")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @TableField(value="ui.user_info_id")
    @JsonProperty("user_info_id")
    private Integer userInfoId;










}

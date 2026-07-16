package com.kuafu.web.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.*;
import com.kuafu.common.entity.StaticResource;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kuafu.web.annotation.IsNotNullField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import com.kuafu.common.annotation.*;
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
@TableName("user_info")
public class UserInfo  {
    @TableId(value = "user_info_id", type = IdType.AUTO)
    @JsonProperty("user_info_id")
    
    
    
    private Integer userInfoId;
    @JsonProperty("name")
    @IsNotNullField(description = "姓名")
    
    
    @TableField("name")
    private String name;
    @JsonProperty("student_id")
    @IsNotNullField(description = "学号")
    
    
    @TableField("student_id")
    private String studentId;
    @JsonProperty("phone_number")
    @IsNotNullField(description = "手机号")
    
    
    @TableField("phone_number")
    private String phoneNumber;
    @JsonProperty("college")
    @IsNotNullField(description = "所属学院")
    
    
    @TableField("college")
    private String college;
    @JsonProperty("major")
    @IsNotNullField(description = "专业")
    
    
    @TableField("major")
    private String major;
    @JsonProperty("password")
    @IsNotNullField(description = "密码")
    
    
    @TableField("password")
    private String password;
    @JsonProperty("role_enum_role_enum_id_1")
    @IsNotNullField(description = "角色")
    
    
    @TableField("role_enum_role_enum_id_1")
    private Integer roleEnumRoleEnumId1;
    @JsonProperty("user_status_enum_user_status_enum_id_1")
    @IsNotNullField(description = "用户状态")
    
    
    @TableField("user_status_enum_user_status_enum_id_1")
    private Integer userStatusEnumUserStatusEnumId1;
    @JsonProperty("registration_date")
    
    
    @TableField("registration_date")
    private Date registrationDate;





}

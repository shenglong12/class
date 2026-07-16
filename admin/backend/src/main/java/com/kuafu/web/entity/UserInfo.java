package com.kuafu.web.entity;

import java.util.Date;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuafu.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;
import com.kuafu.common.annotation.Excel.*;
 import  com.kuafu.web.event.BaseLoginVo; 


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
public class UserInfo   extends BaseLoginVo   {
    @TableId(value = "user_info_id", type = IdType.AUTO)
    @JsonProperty(value = "userInfoId")
    @Excel(name = "主键")

    private Integer userInfoId;
    @JsonProperty(value = "name")
    @Excel(name = "姓名")
    @ExcelProperty(value = "姓名")
    @TableField(value = "name")

    private String name;
    @JsonProperty(value = "studentId")
    @Excel(name = "学号")
    @ExcelProperty(value = "学号")
    @TableField(value = "student_id")

    private String studentId;
    @JsonProperty(value = "phoneNumber")
    @Excel(name = "手机号")
    @ExcelProperty(value = "手机号")
    @TableField(value = "phone_number")

    private String phoneNumber;
    @JsonProperty(value = "college")
    @Excel(name = "所属学院")
    @ExcelProperty(value = "所属学院")
    @TableField(value = "college")

    private String college;
    @JsonProperty(value = "major")
    @Excel(name = "专业")
    @ExcelProperty(value = "专业")
    @TableField(value = "major")

    private String major;
    @JsonProperty(value = "password")
    @Excel(name = "密码")
    @ExcelProperty(value = "密码")
    @TableField(value = "password")

    private String password;
    @JsonProperty(value = "roleEnumRoleEnumId1")
    @Excel(name = "角色")
    @ExcelProperty(value = "角色")
    @TableField(value = "role_enum_role_enum_id_1")

    private Integer roleEnumRoleEnumId1;
    @JsonProperty(value = "userStatusEnumUserStatusEnumId1")
    @Excel(name = "用户状态")
    @ExcelProperty(value = "用户状态")
    @TableField(value = "user_status_enum_user_status_enum_id_1")

    private Integer userStatusEnumUserStatusEnumId1;
    @JsonProperty(value = "registrationDate")
    @Excel(name = "注册时间")
    @ExcelProperty(value = "注册时间")
    @TableField(value = "registration_date")

    private Date registrationDate;


}

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



/**
 * <p>  登录表 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("login")
public class Login    {
    @TableId(value = "login_id", type = IdType.AUTO)
    @JsonProperty(value = "loginId")
    @Excel(name = "主键")

    private Integer loginId;
    @JsonProperty(value = "relevanceId")
    @Excel(name = "绑定业务ID")
    @ExcelProperty(value = "绑定业务ID")
    @TableField(value = "relevance_id")

    private String relevanceId;
    @JsonProperty(value = "password")
    @Excel(name = "密码")
    @ExcelProperty(value = "密码")
    @TableField(value = "password")

    private String password;
    @JsonProperty(value = "userName")
    @Excel(name = "用户昵称")
    @ExcelProperty(value = "用户昵称")
    @TableField(value = "user_name")

    private String userName;
    @JsonProperty(value = "relevanceTable")
    @Excel(name = "绑定业务")
    @ExcelProperty(value = "绑定业务")
    @TableField(value = "relevance_table")

    private String relevanceTable;
    @JsonProperty(value = "phoneNumber")
    @Excel(name = "手机号/用户名")
    @ExcelProperty(value = "手机号/用户名")
    @TableField(value = "phone_number")

    private String phoneNumber;
    @JsonProperty(value = "wxOpenId")
    @Excel(name = "微信绑定ID")
    @ExcelProperty(value = "微信绑定ID")
    @TableField(value = "wx_open_id")

    private String wxOpenId;


}

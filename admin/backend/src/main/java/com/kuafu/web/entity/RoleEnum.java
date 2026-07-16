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
 * <p>  角色枚举 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_enum")
public class RoleEnum    {
    @TableId(value = "role_enum_id", type = IdType.AUTO)
    @JsonProperty(value = "roleEnumId")
    @Excel(name = "主键")

    private Integer roleEnumId;
    @JsonProperty(value = "roleName")
    @Excel(name = "角色名称")
    @ExcelProperty(value = "角色名称")
    @TableField(value = "role_name")

    private String roleName;


}

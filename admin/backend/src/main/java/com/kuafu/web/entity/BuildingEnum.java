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
 * <p>  教学楼枚举 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("building_enum")
public class BuildingEnum    {
    @TableId(value = "building_enum_id", type = IdType.AUTO)
    @JsonProperty(value = "buildingEnumId")
    @Excel(name = "主键")

    private Integer buildingEnumId;
    @JsonProperty(value = "buildingName")
    @Excel(name = "教学楼名称")
    @ExcelProperty(value = "教学楼名称")
    @TableField(value = "building_name")

    private String buildingName;


}

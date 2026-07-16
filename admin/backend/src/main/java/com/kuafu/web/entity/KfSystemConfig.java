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
 * <p>  系统配置 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("kf_system_config")
public class KfSystemConfig    {
    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty(value = "id")
    @Excel(name = "主键")

    private Integer id;
    @JsonProperty(value = "name")
    @Excel(name = "配置项名称")
    @ExcelProperty(value = "配置项名称")
    @TableField(value = "name")

    private String name;
    @JsonProperty(value = "chineseName")
    @Excel(name = "中文名称")
    @ExcelProperty(value = "中文名称")
    @TableField(value = "chinese_name")

    private String chineseName;
    @JsonProperty(value = "description")
    @Excel(name = "配置描述")
    @ExcelProperty(value = "配置描述")
    @TableField(value = "description")

    private String description;
    @JsonProperty(value = "content")
    @Excel(name = "配置值")
    @ExcelProperty(value = "配置值")
    @TableField(value = "content")

    private String content;
    @JsonProperty(value = "remark")
    @Excel(name = "备注")
    @ExcelProperty(value = "备注")
    @TableField(value = "remark")

    private String remark;
    @JsonProperty(value = "type")
    @Excel(name = "配置类型")
    @ExcelProperty(value = "配置类型")
    @TableField(value = "type")

    private String type;


}

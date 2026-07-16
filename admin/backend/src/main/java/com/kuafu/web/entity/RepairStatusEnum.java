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
 * <p>  报修状态 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("repair_status_enum")
public class RepairStatusEnum    {
    @TableId(value = "repair_status_enum_id", type = IdType.AUTO)
    @JsonProperty(value = "repairStatusEnumId")
    @Excel(name = "主键")

    private Integer repairStatusEnumId;
    @JsonProperty(value = "statusName")
    @Excel(name = "状态名称")
    @ExcelProperty(value = "状态名称")
    @TableField(value = "status_name")

    private String statusName;


}

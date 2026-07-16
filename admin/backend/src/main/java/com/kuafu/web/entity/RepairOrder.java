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
@TableName("repair_order")
public class RepairOrder    {
    @TableId(value = "repair_order_id", type = IdType.AUTO)
    @JsonProperty(value = "repairOrderId")
    @Excel(name = "主键")

    private Integer repairOrderId;
    @JsonProperty(value = "userInfoUserInfoId1")
    @Excel(name = "报修用户")
    @ExcelProperty(value = "报修用户")
    @TableField(value = "user_info_user_info_id_1")

    private Integer userInfoUserInfoId1;
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    @Excel(name = "教室")
    @ExcelProperty(value = "教室")
    @TableField(value = "classroom_info_classroom_info_id_1")

    private Integer classroomInfoClassroomInfoId1;
    @JsonProperty(value = "content")
    @Excel(name = "报修内容")
    @ExcelProperty(value = "报修内容")
    @TableField(value = "content")

    private String content;
    @JsonProperty(value = "repairStatusEnumRepairStatusEnumId1")
    @Excel(name = "报修状态")
    @ExcelProperty(value = "报修状态")
    @TableField(value = "repair_status_enum_repair_status_enum_id_1")

    private Integer repairStatusEnumRepairStatusEnumId1;
    @JsonProperty(value = "feedback")
    @Excel(name = "处理反馈")
    @ExcelProperty(value = "处理反馈")
    @TableField(value = "feedback")

    private String feedback;
    @JsonProperty(value = "createTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "创建时间")
    @ExcelProperty(value = "创建时间")
    @TableField(value = "create_time")

    private Date createTime;
    @JsonProperty(value = "imageFilesResourceKey")
    @Excel(cellType = ColumnType.IMAGE, name = "报修图片")
    @ExcelProperty(value = "报修图片")
    @TableField(value = "image_files_resource_key")

    private String imageFilesResourceKey;

    @TableField(exist = false)
    private List<StaticResource> imageFiles;

}

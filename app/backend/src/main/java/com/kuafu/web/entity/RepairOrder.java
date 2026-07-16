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
public class RepairOrder  {
    @TableId(value = "repair_order_id", type = IdType.AUTO)
    @JsonProperty("repair_order_id")
    
    
    
    private Integer repairOrderId;
    @JsonProperty("user_info_user_info_id_1")
    @IsNotNullField(description = "报修用户")
    
    
    @TableField("user_info_user_info_id_1")
    private Integer userInfoUserInfoId1;
    @JsonProperty("classroom_info_classroom_info_id_1")
    @IsNotNullField(description = "教室")
    
    
    @TableField("classroom_info_classroom_info_id_1")
    private Integer classroomInfoClassroomInfoId1;
    @JsonProperty("content")
    @IsNotNullField(description = "报修内容")
    
    
    @TableField("content")
    private String content;
    @JsonProperty("repair_status_enum_repair_status_enum_id_1")
    @IsNotNullField(description = "报修状态")
    
    
    @TableField("repair_status_enum_repair_status_enum_id_1")
    private Integer repairStatusEnumRepairStatusEnumId1;
    @JsonProperty("feedback")
    
    
    @TableField("feedback")
    private String feedback;
    @JsonProperty("create_time")
    @TableField(fill = FieldFill.INSERT)
    
    
    private Date createTime;



@TableField(exist = false)
@JsonProperty("image_files")
private List<StaticResource> imageFiles;



}

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
 * <p>  教室信息 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("classroom_info")
public class ClassroomInfo  {
    @TableId(value = "classroom_info_id", type = IdType.AUTO)
    @JsonProperty("classroom_info_id")
    
    
    
    private Integer classroomInfoId;
    @JsonProperty("building_enum_building_enum_id_1")
    @IsNotNullField(description = "教学楼")
    
    
    @TableField("building_enum_building_enum_id_1")
    private Integer buildingEnumBuildingEnumId1;
    @JsonProperty("room_number")
    @IsNotNullField(description = "教室编号")
    
    
    @TableField("room_number")
    private String roomNumber;
    @JsonProperty("qr_code_path")
    
    
    @TableField("qr_code_path")
    private String qrCodePath;





}

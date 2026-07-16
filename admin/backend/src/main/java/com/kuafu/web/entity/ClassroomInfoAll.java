package com.kuafu.web.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuafu.common.annotation.Excel;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.kuafu.common.annotation.Excel.*;

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
public class ClassroomInfoAll  {
    @TableField(value="be.building_name")
    @JsonProperty(value = "buildingName")
    @Excel(name = "教学楼名称")
    private String buildingName;
    @TableField(value="ci.classroom_info_id")
    @JsonProperty(value = "classroomInfoId")
    @Excel(name = "主键")
    private Integer classroomInfoId;
    @TableField(value="ci.building_enum_building_enum_id_1")
    @JsonProperty(value = "buildingEnumBuildingEnumId1")
    @Excel(name = "教学楼")
    private Integer buildingEnumBuildingEnumId1;
    @TableField(value="ci.room_number")
    @JsonProperty(value = "roomNumber")
    @Excel(name = "教室编号")
    private String roomNumber;
    @TableField(value="ci.qr_code_path")
    @JsonProperty(value = "qrCodePath")
    @Excel(name = "二维码路径")
    private String qrCodePath;

}

package com.kuafu.web.entity;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import com.kuafu.common.entity.StaticResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



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
public class ClassroomInfoAll {
    @TableField(value="be.building_name")
    @JsonProperty("building_name")
    private String buildingName;
    @TableField(value="ci.building_enum_building_enum_id_1")
    @JsonProperty("building_enum_building_enum_id_1")
    private Integer buildingEnumBuildingEnumId1;
    @TableField(value="ci.room_number")
    @JsonProperty("room_number")
    private String roomNumber;
    @TableField(value="ci.qr_code_path")
    @JsonProperty("qr_code_path")
    private String qrCodePath;
    @TableField(value="ci.classroom_info_id")
    @JsonProperty("classroom_info_id")
    private Integer classroomInfoId;










}

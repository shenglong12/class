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
public class ClassroomInfo    {
    @TableId(value = "classroom_info_id", type = IdType.AUTO)
    @JsonProperty(value = "classroomInfoId")
    @Excel(name = "主键")

    private Integer classroomInfoId;
    @JsonProperty(value = "buildingEnumBuildingEnumId1")
    @Excel(name = "教学楼")
    @ExcelProperty(value = "教学楼")
    @TableField(value = "building_enum_building_enum_id_1")

    private Integer buildingEnumBuildingEnumId1;
    @JsonProperty(value = "roomNumber")
    @Excel(name = "教室编号")
    @ExcelProperty(value = "教室编号")
    @TableField(value = "room_number")

    private String roomNumber;
    @JsonProperty(value = "qrCodePath")
    @Excel(name = "二维码路径")
    @ExcelProperty(value = "二维码路径")
    @TableField(value = "qr_code_path")

    private String qrCodePath;


}

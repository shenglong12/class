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
 * <p>  扫码记录 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("scan_record")
public class ScanRecord    {
    @TableId(value = "scan_id", type = IdType.AUTO)
    @JsonProperty(value = "scanId")
    @Excel(name = "主键ID")

    private Integer scanId;
    @JsonProperty(value = "userInfoUserInfoId1")
    @Excel(name = "用户ID")
    @ExcelProperty(value = "用户ID")
    @TableField(value = "user_info_user_info_id_1")

    private Integer userInfoUserInfoId1;
    @JsonProperty(value = "buildingName")
    @Excel(name = "教学楼名称")
    @ExcelProperty(value = "教学楼名称")
    @TableField(value = "building_name")

    private String buildingName;
    @JsonProperty(value = "roomNumber")
    @Excel(name = "教室编号")
    @ExcelProperty(value = "教室编号")
    @TableField(value = "room_number")

    private String roomNumber;
    @JsonProperty(value = "scanTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "扫码时间")
    @ExcelProperty(value = "扫码时间")
    @TableField(value = "scan_time")

    private Date scanTime;


}

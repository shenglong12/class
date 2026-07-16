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
public class ScanRecord  {
    @TableId(value = "scan_id", type = IdType.AUTO)
    @JsonProperty("scan_id")
    
    
    
    private Integer scanId;
    @JsonProperty("user_info_user_info_id_1")
    
    
    @TableField("user_info_user_info_id_1")
    private Integer userInfoUserInfoId1;
    @JsonProperty("building_name")
    
    
    @TableField("building_name")
    private String buildingName;
    @JsonProperty("room_number")
    
    
    @TableField("room_number")
    private String roomNumber;
    @JsonProperty("scan_time")
    @IsNotNullField(description = "扫码时间")
    
    
    @TableField("scan_time")
    private Date scanTime;





}

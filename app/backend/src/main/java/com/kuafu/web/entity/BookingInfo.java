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
 * <p>  预约信息 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("booking_info")
public class BookingInfo  {
    @TableId(value = "booking_info_id", type = IdType.AUTO)
    @JsonProperty("booking_info_id")
    
    
    
    private Integer bookingInfoId;
    @JsonProperty("user_info_user_info_id_1")
    @IsNotNullField(description = "预约用户")
    
    
    @TableField("user_info_user_info_id_1")
    private Integer userInfoUserInfoId1;
    @JsonProperty("classroom_info_classroom_info_id_1")
    @IsNotNullField(description = "教室")
    
    
    @TableField("classroom_info_classroom_info_id_1")
    private Integer classroomInfoClassroomInfoId1;
    @JsonProperty("booking_date")
    @IsNotNullField(description = "预约日期")
    
    
    @TableField("booking_date")
    private Date bookingDate;
    @JsonProperty("time_period")
    @IsNotNullField(description = "时间段")
    
    
    @TableField("time_period")
    private String timePeriod;
    @JsonProperty("purpose")
    @IsNotNullField(description = "预约用途")
    
    
    @TableField("purpose")
    private String purpose;
    @JsonProperty("booking_status_enum_booking_status_enum_id_1")
    @IsNotNullField(description = "预约状态")
    
    
    @TableField("booking_status_enum_booking_status_enum_id_1")
    private Integer bookingStatusEnumBookingStatusEnumId1;
    @JsonProperty("create_time")
    @TableField(fill = FieldFill.INSERT)
    
    
    private Date createTime;
    @JsonProperty("start_time")
    @IsNotNullField(description = "预约开始时间")
    
    
    @TableField("start_time")
    private String startTime;
    @JsonProperty("end_time")
    @IsNotNullField(description = "预约结束时间")
    
    
    @TableField("end_time")
    private String endTime;





}

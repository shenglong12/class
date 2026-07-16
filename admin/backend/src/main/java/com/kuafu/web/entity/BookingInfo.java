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
public class BookingInfo    {
    @TableId(value = "booking_info_id", type = IdType.AUTO)
    @JsonProperty(value = "bookingInfoId")
    @Excel(name = "主键")

    private Integer bookingInfoId;
    @JsonProperty(value = "userInfoUserInfoId1")
    @Excel(name = "预约用户")
    @ExcelProperty(value = "预约用户")
    @TableField(value = "user_info_user_info_id_1")

    private Integer userInfoUserInfoId1;
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    @Excel(name = "教室")
    @ExcelProperty(value = "教室")
    @TableField(value = "classroom_info_classroom_info_id_1")

    private Integer classroomInfoClassroomInfoId1;
    @JsonProperty(value = "bookingDate")
    @Excel(name = "预约日期")
    @ExcelProperty(value = "预约日期")
    @TableField(value = "booking_date")

    private Date bookingDate;
    @JsonProperty(value = "timePeriod")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "时间段")
    @ExcelProperty(value = "时间段")
    @TableField(value = "time_period")

    private String timePeriod;
    @JsonProperty(value = "purpose")
    @Excel(name = "预约用途")
    @ExcelProperty(value = "预约用途")
    @TableField(value = "purpose")

    private String purpose;
    @JsonProperty(value = "bookingStatusEnumBookingStatusEnumId1")
    @Excel(name = "预约状态")
    @ExcelProperty(value = "预约状态")
    @TableField(value = "booking_status_enum_booking_status_enum_id_1")

    private Integer bookingStatusEnumBookingStatusEnumId1;
    @JsonProperty(value = "createTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "创建时间")
    @ExcelProperty(value = "创建时间")
    @TableField(value = "create_time")

    private Date createTime;
    @JsonProperty(value = "startTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "预约开始时间")
    @ExcelProperty(value = "预约开始时间")
    @TableField(value = "start_time")

    private String startTime;
    @JsonProperty(value = "endTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "预约结束时间")
    @ExcelProperty(value = "预约结束时间")
    @TableField(value = "end_time")

    private String endTime;


}

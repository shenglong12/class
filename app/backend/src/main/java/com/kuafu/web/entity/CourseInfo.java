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
 * <p>  课程信息 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("course_info")
public class CourseInfo  {
    @TableId(value = "course_info_id", type = IdType.AUTO)
    @JsonProperty("course_info_id")
    
    
    
    private Integer courseInfoId;
    @JsonProperty("course_name")
    @IsNotNullField(description = "课程名称")
    
    
    @TableField("course_name")
    private String courseName;
    @JsonProperty("college")
    @IsNotNullField(description = "学院")
    
    
    @TableField("college")
    private String college;
    @JsonProperty("major")
    @IsNotNullField(description = "专业")
    
    
    @TableField("major")
    private String major;
    @JsonProperty("classroom_info_classroom_info_id_1")
    @IsNotNullField(description = "教室")
    
    
    @TableField("classroom_info_classroom_info_id_1")
    private Integer classroomInfoClassroomInfoId1;
    @JsonProperty("start_date")
    @IsNotNullField(description = "开始日期")
    
    
    @TableField("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    @IsNotNullField(description = "结束日期")
    
    
    @TableField("end_date")
    private Date endDate;
    @JsonProperty("weekdays")
    @IsNotNullField(description = "上课星期")
    
    
    @TableField("weekdays")
    private String weekdays;
    @JsonProperty("time_period")
    @IsNotNullField(description = "时间段")
    
    
    @TableField("time_period")
    private String timePeriod;
    @JsonProperty("content")
    
    
    @TableField("content")
    private String content;





}

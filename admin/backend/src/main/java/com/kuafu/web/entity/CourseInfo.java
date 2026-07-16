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
public class CourseInfo    {
    @TableId(value = "course_info_id", type = IdType.AUTO)
    @JsonProperty(value = "courseInfoId")
    @Excel(name = "主键")

    private Integer courseInfoId;
    @JsonProperty(value = "courseName")
    @Excel(name = "课程名称")
    @ExcelProperty(value = "课程名称")
    @TableField(value = "course_name")

    private String courseName;
    @JsonProperty(value = "college")
    @Excel(name = "学院")
    @ExcelProperty(value = "学院")
    @TableField(value = "college")

    private String college;
    @JsonProperty(value = "major")
    @Excel(name = "专业")
    @ExcelProperty(value = "专业")
    @TableField(value = "major")

    private String major;
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    @Excel(name = "教室")
    @ExcelProperty(value = "教室")
    @TableField(value = "classroom_info_classroom_info_id_1")

    private Integer classroomInfoClassroomInfoId1;
    @JsonProperty(value = "startDate")
    @Excel(name = "开始日期")
    @ExcelProperty(value = "开始日期")
    @TableField(value = "start_date")

    private Date startDate;
    @JsonProperty(value = "endDate")
    @Excel(name = "结束日期")
    @ExcelProperty(value = "结束日期")
    @TableField(value = "end_date")

    private Date endDate;
    @JsonProperty(value = "weekdays")
    @Excel(name = "上课星期")
    @ExcelProperty(value = "上课星期")
    @TableField(value = "weekdays")

    private String weekdays;
    @JsonProperty(value = "timePeriod")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "时间段")
    @ExcelProperty(value = "时间段")
    @TableField(value = "time_period")

    private String timePeriod;
    @JsonProperty(value = "content")
    @Excel(name = "授课内容")
    @ExcelProperty(value = "授课内容")
    @TableField(value = "content")

    private String content;


}

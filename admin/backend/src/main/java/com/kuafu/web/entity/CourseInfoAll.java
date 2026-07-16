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
public class CourseInfoAll  {
    @TableField(value="ci.college")
    @JsonProperty(value = "college")
    @Excel(name = "学院")
    private String college;
    @TableField(value="ci.course_name")
    @JsonProperty(value = "courseName")
    @Excel(name = "课程名称")
    private String courseName;
    @TableField(value="ci.weekdays")
    @JsonProperty(value = "weekdays")
    @Excel(name = "上课星期")
    private String weekdays;
    @TableField(value="ci1.room_number")
    @JsonProperty(value = "roomNumber")
    @Excel(name = "教室编号")
    private String roomNumber;
    @TableField(value="ci1.qr_code_path")
    @JsonProperty(value = "qrCodePath")
    @Excel(name = "二维码路径")
    private String qrCodePath;
    @TableField(value="ci.course_info_id")
    @JsonProperty(value = "courseInfoId")
    @Excel(name = "主键")
    private Integer courseInfoId;
    @TableField(value="ci.start_date")
    @JsonProperty(value = "startDate")
    @Excel(name = "开始日期")
    private Date startDate;
    @TableField(value="ci.content")
    @JsonProperty(value = "content")
    @Excel(name = "授课内容")
    private String content;
    @TableField(value="ci.end_date")
    @JsonProperty(value = "endDate")
    @Excel(name = "结束日期")
    private Date endDate;
    @TableField(value="ci.major")
    @JsonProperty(value = "major")
    @Excel(name = "专业")
    private String major;
    @TableField(value="ci1.building_enum_building_enum_id_1")
    @JsonProperty(value = "buildingEnumBuildingEnumId1")
    @Excel(name = "教学楼")
    private Integer buildingEnumBuildingEnumId1;
    @TableField(value="ci.classroom_info_classroom_info_id_1")
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    @Excel(name = "教室")
    private Integer classroomInfoClassroomInfoId1;
    @TableField(value="ci.time_period")
    @JsonProperty(value = "timePeriod")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "时间段")
    private String timePeriod;

}

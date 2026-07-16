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
public class CourseInfoAll {
    @TableField(value="ci.college")
    @JsonProperty("college")
    private String college;
    @TableField(value="ci.end_date")
    @JsonProperty("end_date")
    private Date endDate;
    @TableField(value="ci.course_name")
    @JsonProperty("course_name")
    private String courseName;
    @TableField(value="ci.classroom_info_classroom_info_id_1")
    @JsonProperty("classroom_info_classroom_info_id_1")
    private Integer classroomInfoClassroomInfoId1;
    @TableField(value="ci.weekdays")
    @JsonProperty("weekdays")
    private String weekdays;
    @TableField(value="ci1.room_number")
    @JsonProperty("room_number")
    private String roomNumber;
    @TableField(value="ci1.qr_code_path")
    @JsonProperty("qr_code_path")
    private String qrCodePath;
    @TableField(value="ci.content")
    @JsonProperty("content")
    private String content;
    @TableField(value="ci1.building_enum_building_enum_id_1")
    @JsonProperty("building_enum_building_enum_id_1")
    private Integer buildingEnumBuildingEnumId1;
    @TableField(value="ci.major")
    @JsonProperty("major")
    private String major;
    @TableField(value="ci.course_info_id")
    @JsonProperty("course_info_id")
    private Integer courseInfoId;
    @TableField(value="ci.time_period")
    @JsonProperty("time_period")
    private String timePeriod;
    @TableField(value="ci.start_date")
    @JsonProperty("start_date")
    private Date startDate;










}

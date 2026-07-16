package com.kuafu.web.vo;

import java.util.Date;

import com.kuafu.common.domin.PageRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>课程信息-分页列表-响应参数</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CourseInfoPageVO extends PageRequest {

    @JsonProperty(value = "courseInfoId")
    private Integer courseInfoId;
    @JsonProperty(value = "courseName")
    private String courseName;
    @JsonProperty(value = "college")
    private String college;
    @JsonProperty(value = "major")
    private String major;
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    private Integer classroomInfoClassroomInfoId1;
    @JsonProperty(value = "startDate")
    private Date startDate;
    @JsonProperty(value = "endDate")
    private Date endDate;
    @JsonProperty(value = "weekdays")
    private String weekdays;
    @JsonProperty(value = "timePeriod")
    private String timePeriod;
    @JsonProperty(value = "content")
    private String content;

}

package com.kuafu.web.vo;

import com.kuafu.common.domin.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CourseInfoAllPageVO extends PageRequest {

    @JsonProperty(value = "college")
    private String college;
    @JsonProperty(value = "courseName")
    private String courseName;
    @JsonProperty(value = "weekdays")
    private String weekdays;
    @JsonProperty(value = "roomNumber")
    private String roomNumber;
    @JsonProperty(value = "qrCodePath")
    private String qrCodePath;
    @JsonProperty(value = "courseInfoId")
    private Integer courseInfoId;
    @JsonProperty(value = "startDate")
    private Date startDate;
    @JsonProperty(value = "content")
    private String content;
    @JsonProperty(value = "endDate")
    private Date endDate;
    @JsonProperty(value = "major")
    private String major;
    @JsonProperty(value = "buildingEnumBuildingEnumId1")
    private Integer buildingEnumBuildingEnumId1;
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    private Integer classroomInfoClassroomInfoId1;
    @JsonProperty(value = "timePeriod")
    private String timePeriod;

}

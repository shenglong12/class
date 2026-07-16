package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

/**
 * <p>课程信息</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInfoVO  {

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

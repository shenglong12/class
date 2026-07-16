package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.kuafu.common.entity.StaticResource;
import java.util.List;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kuafu.common.deserializer.MultiDateDeserializer;
import com.kuafu.common.entity.BaseEntity;
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
public class CourseInfoVO  extends BaseEntity {

    @JsonProperty("courseInfoId")
    private Integer courseInfoId;
    @JsonProperty("courseName")
    private String courseName;
    @JsonProperty("college")
    private String college;
    @JsonProperty("major")
    private String major;
    @JsonProperty("classroomInfoClassroomInfoId1")
    private Integer classroomInfoClassroomInfoId1;
    @JsonDeserialize(using = MultiDateDeserializer.class)
    @JsonProperty("startDate")
    private Date startDate;
    @JsonDeserialize(using = MultiDateDeserializer.class)
    @JsonProperty("endDate")
    private Date endDate;
    @JsonProperty("weekdays")
    private String weekdays;
    @JsonProperty("timePeriod")
    private String timePeriod;
    @JsonProperty("content")
    private String content;




}

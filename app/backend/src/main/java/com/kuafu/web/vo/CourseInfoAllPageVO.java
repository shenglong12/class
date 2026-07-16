package com.kuafu.web.vo;

import com.kuafu.common.domin.PageRequest;
import com.kuafu.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kuafu.common.deserializer.MultiDateDeserializer;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;
import com.kuafu.common.entity.StaticResource;

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
public class CourseInfoAllPageVO extends BaseEntity {

@JsonProperty("college")
    private String college;
    @JsonDeserialize(using = MultiDateDeserializer.class)
@JsonProperty("endDate")
    private Date endDate;
@JsonProperty("courseName")
    private String courseName;
@JsonProperty("classroomInfoClassroomInfoId1")
    private Integer classroomInfoClassroomInfoId1;
@JsonProperty("weekdays")
    private String weekdays;
@JsonProperty("roomNumber")
    private String roomNumber;
@JsonProperty("qrCodePath")
    private String qrCodePath;
@JsonProperty("content")
    private String content;
@JsonProperty("buildingEnumBuildingEnumId1")
    private Integer buildingEnumBuildingEnumId1;
@JsonProperty("major")
    private String major;
@JsonProperty("courseInfoId")
    private Integer courseInfoId;
@JsonProperty("timePeriod")
    private String timePeriod;
    @JsonDeserialize(using = MultiDateDeserializer.class)
@JsonProperty("startDate")
    private Date startDate;

}

package com.kuafu.web.vo;

import java.util.Date;

import com.kuafu.common.domin.PageRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>预约信息-分页列表-响应参数</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingInfoPageVO extends PageRequest {

    @JsonProperty(value = "bookingInfoId")
    private Integer bookingInfoId;
    @JsonProperty(value = "userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    private Integer classroomInfoClassroomInfoId1;
    @JsonProperty(value = "bookingDate")
    private Date bookingDate;
    @JsonProperty(value = "timePeriod")
    private String timePeriod;
    @JsonProperty(value = "purpose")
    private String purpose;
    @JsonProperty(value = "bookingStatusEnumBookingStatusEnumId1")
    private Integer bookingStatusEnumBookingStatusEnumId1;
    @JsonProperty(value = "createTime")
    private Date createTime;
    @JsonProperty(value = "startTime")
    private String startTime;
    @JsonProperty(value = "endTime")
    private String endTime;

}

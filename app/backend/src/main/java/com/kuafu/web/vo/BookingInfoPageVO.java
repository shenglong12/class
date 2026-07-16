package com.kuafu.web.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kuafu.common.deserializer.MultiDateDeserializer;
import com.kuafu.common.domin.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;
import com.kuafu.common.entity.StaticResource;
import com.kuafu.common.entity.BaseEntity;
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
public class BookingInfoPageVO extends BaseEntity {

@JsonProperty("bookingInfoId")
    private Integer bookingInfoId;
@JsonProperty("userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
@JsonProperty("classroomInfoClassroomInfoId1")
    private Integer classroomInfoClassroomInfoId1;
    @JsonDeserialize(using = MultiDateDeserializer.class)
@JsonProperty("bookingDate")
    private Date bookingDate;
@JsonProperty("timePeriod")
    private String timePeriod;
@JsonProperty("purpose")
    private String purpose;
@JsonProperty("bookingStatusEnumBookingStatusEnumId1")
    private Integer bookingStatusEnumBookingStatusEnumId1;
    @JsonDeserialize(using = MultiDateDeserializer.class)
@JsonProperty("createTime")
    private Date createTime;
@JsonProperty("startTime")
    private String startTime;
@JsonProperty("endTime")
    private String endTime;


}

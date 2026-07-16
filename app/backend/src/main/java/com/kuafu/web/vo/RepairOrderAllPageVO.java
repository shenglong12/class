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
 * <p>报修单-分页列表-响应参数</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RepairOrderAllPageVO extends BaseEntity {

@JsonProperty("college")
    private String college;
@JsonProperty("userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
    @JsonDeserialize(using = MultiDateDeserializer.class)
@JsonProperty("createTime")
    private Date createTime;
@JsonProperty("classroomInfoClassroomInfoId1")
    private Integer classroomInfoClassroomInfoId1;
@JsonProperty("statusName")
    private String statusName;
@JsonProperty("studentId")
    private String studentId;
@JsonProperty("roleEnumRoleEnumId1")
    private Integer roleEnumRoleEnumId1;
@JsonProperty("roomNumber")
    private String roomNumber;
@JsonProperty("qrCodePath")
    private String qrCodePath;
@JsonProperty("content")
    private String content;
@JsonProperty("repairOrderId")
    private Integer repairOrderId;
@JsonProperty("feedback")
    private String feedback;
@JsonProperty("password")
    private String password;
@JsonProperty("buildingEnumBuildingEnumId1")
    private Integer buildingEnumBuildingEnumId1;
    @JsonDeserialize(using = MultiDateDeserializer.class)
@JsonProperty("registrationDate")
    private Date registrationDate;
@JsonProperty("major")
    private String major;
@JsonProperty("name")
    private String name;
@JsonProperty("userStatusEnumUserStatusEnumId1")
    private Integer userStatusEnumUserStatusEnumId1;
@JsonProperty("phoneNumber")
    private String phoneNumber;
@JsonProperty("repairStatusEnumRepairStatusEnumId1")
    private Integer repairStatusEnumRepairStatusEnumId1;

}

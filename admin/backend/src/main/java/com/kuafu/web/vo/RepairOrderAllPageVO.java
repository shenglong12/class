package com.kuafu.web.vo;

import com.kuafu.common.domin.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RepairOrderAllPageVO extends PageRequest {

    @JsonProperty(value = "college")
    private String college;
    @JsonProperty(value = "userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
    @JsonProperty(value = "createTime")
    private Date createTime;
    @JsonProperty(value = "roleEnumRoleEnumId1")
    private Integer roleEnumRoleEnumId1;
    @JsonProperty(value = "roomNumber")
    private String roomNumber;
    @JsonProperty(value = "qrCodePath")
    private String qrCodePath;
    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;
    @JsonProperty(value = "repairOrderId")
    private Integer repairOrderId;
    @JsonProperty(value = "statusName")
    private String statusName;
    @JsonProperty(value = "registrationDate")
    private Date registrationDate;
    @JsonProperty(value = "content")
    private String content;
    @JsonProperty(value = "userStatusEnumUserStatusEnumId1")
    private Integer userStatusEnumUserStatusEnumId1;
    @JsonProperty(value = "feedback")
    private String feedback;
    @JsonProperty(value = "studentId")
    private String studentId;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "major")
    private String major;
    @JsonProperty(value = "buildingEnumBuildingEnumId1")
    private Integer buildingEnumBuildingEnumId1;
    @JsonProperty(value = "imageFilesResourceKey")
    private String imageFilesResourceKey;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "repairStatusEnumRepairStatusEnumId1")
    private Integer repairStatusEnumRepairStatusEnumId1;
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    private Integer classroomInfoClassroomInfoId1;

}

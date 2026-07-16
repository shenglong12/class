package com.kuafu.web.vo;

import java.util.Date;

import com.kuafu.common.domin.PageRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class RepairOrderPageVO extends PageRequest {

    @JsonProperty(value = "repairOrderId")
    private Integer repairOrderId;
    @JsonProperty(value = "userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
    @JsonProperty(value = "classroomInfoClassroomInfoId1")
    private Integer classroomInfoClassroomInfoId1;
    @JsonProperty(value = "content")
    private String content;
    @JsonProperty(value = "repairStatusEnumRepairStatusEnumId1")
    private Integer repairStatusEnumRepairStatusEnumId1;
    @JsonProperty(value = "feedback")
    private String feedback;
    @JsonProperty(value = "createTime")
    private Date createTime;
    @JsonProperty(value = "imageFilesResourceKey")
    private String imageFilesResourceKey;

}

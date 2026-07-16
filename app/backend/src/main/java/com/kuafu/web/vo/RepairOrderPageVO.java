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
public class RepairOrderPageVO extends BaseEntity {

@JsonProperty("repairOrderId")
    private Integer repairOrderId;
@JsonProperty("userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
@JsonProperty("classroomInfoClassroomInfoId1")
    private Integer classroomInfoClassroomInfoId1;
@JsonProperty("content")
    private String content;
@JsonProperty("repairStatusEnumRepairStatusEnumId1")
    private Integer repairStatusEnumRepairStatusEnumId1;
@JsonProperty("feedback")
    private String feedback;
    @JsonDeserialize(using = MultiDateDeserializer.class)
@JsonProperty("createTime")
    private Date createTime;

private List<StaticResource> imageFiles;

}

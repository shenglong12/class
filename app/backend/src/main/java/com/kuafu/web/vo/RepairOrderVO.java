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
 * <p>报修单</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairOrderVO  extends BaseEntity {

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

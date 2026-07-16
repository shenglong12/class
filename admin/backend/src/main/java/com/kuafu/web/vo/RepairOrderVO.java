package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

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
public class RepairOrderVO  {

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

    @JsonProperty("imageFiles")
    private List<StaticResource> imageFiles;

}

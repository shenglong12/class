package com.kuafu.web.vo;

import com.kuafu.common.domin.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * <p>教室信息-分页列表-响应参数</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClassroomInfoAllPageVO extends PageRequest {

    @JsonProperty(value = "buildingName")
    private String buildingName;
    @JsonProperty(value = "classroomInfoId")
    private Integer classroomInfoId;
    @JsonProperty(value = "buildingEnumBuildingEnumId1")
    private Integer buildingEnumBuildingEnumId1;
    @JsonProperty(value = "roomNumber")
    private String roomNumber;
    @JsonProperty(value = "qrCodePath")
    private String qrCodePath;

}

package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

/**
 * <p>教室信息</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomInfoVO  {

     @JsonProperty(value = "classroomInfoId")
    private Integer classroomInfoId;
     @JsonProperty(value = "buildingEnumBuildingEnumId1")
    private Integer buildingEnumBuildingEnumId1;
     @JsonProperty(value = "roomNumber")
    private String roomNumber;
     @JsonProperty(value = "qrCodePath")
    private String qrCodePath;


}

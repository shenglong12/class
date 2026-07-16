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
public class ClassroomInfoAllPageVO extends BaseEntity {

@JsonProperty("buildingName")
    private String buildingName;
@JsonProperty("buildingEnumBuildingEnumId1")
    private Integer buildingEnumBuildingEnumId1;
@JsonProperty("roomNumber")
    private String roomNumber;
@JsonProperty("qrCodePath")
    private String qrCodePath;
@JsonProperty("classroomInfoId")
    private Integer classroomInfoId;

}

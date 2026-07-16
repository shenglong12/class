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
 * <p>扫码记录</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanRecordVO  extends BaseEntity {

    @JsonProperty("scanId")
    private Integer scanId;
    @JsonProperty("userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
    @JsonProperty("buildingName")
    private String buildingName;
    @JsonProperty("roomNumber")
    private String roomNumber;
    @JsonDeserialize(using = MultiDateDeserializer.class)
    @JsonProperty("scanTime")
    private Date scanTime;




}

package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

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
public class ScanRecordVO  {

     @JsonProperty(value = "scanId")
    private Integer scanId;
     @JsonProperty(value = "userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
     @JsonProperty(value = "buildingName")
    private String buildingName;
     @JsonProperty(value = "roomNumber")
    private String roomNumber;
     @JsonProperty(value = "scanTime")
    private Date scanTime;


}

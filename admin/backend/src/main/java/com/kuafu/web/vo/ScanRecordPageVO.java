package com.kuafu.web.vo;

import java.util.Date;

import com.kuafu.common.domin.PageRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>扫码记录-分页列表-响应参数</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScanRecordPageVO extends PageRequest {

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

package com.kuafu.web.vo;

import com.kuafu.common.domin.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ScanRecordAllPageVO extends PageRequest {

    @JsonProperty(value = "college")
    private String college;
    @JsonProperty(value = "userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
    @JsonProperty(value = "buildingName")
    private String buildingName;
    @JsonProperty(value = "roleEnumRoleEnumId1")
    private Integer roleEnumRoleEnumId1;
    @JsonProperty(value = "scanId")
    private Integer scanId;
    @JsonProperty(value = "roomNumber")
    private String roomNumber;
    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;
    @JsonProperty(value = "registrationDate")
    private Date registrationDate;
    @JsonProperty(value = "userStatusEnumUserStatusEnumId1")
    private Integer userStatusEnumUserStatusEnumId1;
    @JsonProperty(value = "scanTime")
    private Date scanTime;
    @JsonProperty(value = "studentId")
    private String studentId;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "major")
    private String major;
    @JsonProperty(value = "name")
    private String name;

}

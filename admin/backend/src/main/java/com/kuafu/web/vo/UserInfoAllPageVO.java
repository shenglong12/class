package com.kuafu.web.vo;

import com.kuafu.common.domin.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * <p>用户信息-分页列表-响应参数</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserInfoAllPageVO extends PageRequest {

    @JsonProperty(value = "studentId")
    private String studentId;
    @JsonProperty(value = "college")
    private String college;
    @JsonProperty(value = "userInfoId")
    private Integer userInfoId;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "major")
    private String major;
    @JsonProperty(value = "roleEnumRoleEnumId1")
    private Integer roleEnumRoleEnumId1;
    @JsonProperty(value = "roleName")
    private String roleName;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;
    @JsonProperty(value = "statusName")
    private String statusName;
    @JsonProperty(value = "registrationDate")
    private Date registrationDate;
    @JsonProperty(value = "userStatusEnumUserStatusEnumId1")
    private Integer userStatusEnumUserStatusEnumId1;

}

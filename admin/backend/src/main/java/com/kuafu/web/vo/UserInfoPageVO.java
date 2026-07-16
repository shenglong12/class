package com.kuafu.web.vo;

import java.util.Date;

import com.kuafu.common.domin.PageRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class UserInfoPageVO extends PageRequest {

    @JsonProperty(value = "userInfoId")
    private Integer userInfoId;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "studentId")
    private String studentId;
    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;
    @JsonProperty(value = "college")
    private String college;
    @JsonProperty(value = "major")
    private String major;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "roleEnumRoleEnumId1")
    private Integer roleEnumRoleEnumId1;
    @JsonProperty(value = "userStatusEnumUserStatusEnumId1")
    private Integer userStatusEnumUserStatusEnumId1;
    @JsonProperty(value = "registrationDate")
    private Date registrationDate;

}

package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

/**
 * <p>用户信息</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO  {

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

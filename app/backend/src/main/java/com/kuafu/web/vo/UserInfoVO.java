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
 * <p>用户信息</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO  extends BaseEntity {

    @JsonProperty("userInfoId")
    private Integer userInfoId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("studentId")
    private String studentId;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("college")
    private String college;
    @JsonProperty("major")
    private String major;
    @JsonProperty("password")
    private String password;
    @JsonProperty("roleEnumRoleEnumId1")
    private Integer roleEnumRoleEnumId1;
    @JsonProperty("userStatusEnumUserStatusEnumId1")
    private Integer userStatusEnumUserStatusEnumId1;
    @JsonDeserialize(using = MultiDateDeserializer.class)
    @JsonProperty("registrationDate")
    private Date registrationDate;




}

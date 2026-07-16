package com.kuafu.web.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kuafu.common.deserializer.MultiDateDeserializer;
import com.kuafu.common.domin.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;
import com.kuafu.common.entity.StaticResource;
import com.kuafu.common.entity.BaseEntity;
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
public class UserInfoPageVO extends BaseEntity {

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

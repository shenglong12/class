package com.kuafu.web.vo;

import java.util.Date;

import com.kuafu.common.domin.PageRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>登录表-分页列表-响应参数</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginPageVO extends PageRequest {

    @JsonProperty(value = "loginId")
    private Integer loginId;
    @JsonProperty(value = "relevanceId")
    private String relevanceId;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "userName")
    private String userName;
    @JsonProperty(value = "relevanceTable")
    private String relevanceTable;
    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;
    @JsonProperty(value = "wxOpenId")
    private String wxOpenId;

}

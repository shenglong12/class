package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

/**
 * <p>登录表</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO  {

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

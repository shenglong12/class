package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

/**
 * <p>用户状态</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusEnumVO  {

     @JsonProperty(value = "userStatusEnumId")
    private Integer userStatusEnumId;
     @JsonProperty(value = "statusName")
    private String statusName;


}

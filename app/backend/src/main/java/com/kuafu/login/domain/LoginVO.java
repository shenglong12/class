package com.kuafu.login.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;

/**
 * <p>登陆表</p>
 *
 * @author kuafuai
 * @description
 * @date 2024/08/18 14:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO  {

    private Integer loginId;
    private String wxOpenId;
    private String phoneNumber;
    private String userName;
    private String relevanceId;
    private String relevanceTable;

}

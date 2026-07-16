package com.kuafu.login.domain;

import java.util.Date;

import com.kuafu.common.domin.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>登陆表-分页列表-响应参数</p>
 *
 * @author kuafuai
 * @description
 * @date 2024/08/18 14:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginPageVO extends PageRequest {

    private Integer loginId;
    private String wxOpenId;
    private String phoneNumber;
    private String userName;
    private String relevanceId;
    private String relevanceTable;

}

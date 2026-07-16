package com.kuafu.login.handle;


import com.kuafu.common.constant.HttpStatus;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.util.JSON;
import com.kuafu.common.util.ServletUtils;
import com.kuafu.common.util.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
@ConditionalOnProperty(prefix = "login", name = "enable")
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(ResultUtils.error(code, msg)));
    }
}

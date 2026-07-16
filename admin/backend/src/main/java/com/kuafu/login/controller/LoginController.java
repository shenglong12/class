package com.kuafu.login.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.kuafu.common.annotation.Log;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.login.LoginUser;
import com.kuafu.common.util.StringUtils;
import com.kuafu.login.model.LoginVo;
import com.kuafu.login.provider.WxAppAuthentication;
import com.kuafu.login.provider.WxWebAuthentication;
import com.kuafu.login.service.LoginBusinessService;
import com.kuafu.login.service.TokenService;
import com.kuafu.login.service.WxAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@Slf4j
@ConditionalOnProperty(prefix = "login", name = "enable")
@Api(value = "LoginController", tags = {"登陆"})
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WxAppService wxAppService;

    @Autowired
    private LoginBusinessService loginBusinessService;

    @PostMapping("/login/wxApp")
    @ApiOperation("小程序CODE登陆")
    @ApiOperationSupport(includeParameters = {"loginVo.code"})
    public BaseResponse login(@RequestBody LoginVo loginVo) {
        WxAppAuthentication authentication = new WxAppAuthentication(loginVo);
        Authentication returnAuth = authenticationManager.authenticate(authentication);
        LoginUser loginUser = (LoginUser) returnAuth.getPrincipal();
        String token = tokenService.createToken(loginUser);
        return ResultUtils.success(token);
    }

    @PostMapping("/login/passwd")
    @ApiOperation("用户名密码登陆")
    @ApiOperationSupport(includeParameters = {"loginVo.phone", "loginVo.password"})
    public BaseResponse loginByPasswd(@RequestBody LoginVo loginVo) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVo.getPhone(), loginVo.getPassword());
        Authentication returnAuth = authenticationManager.authenticate(authenticationToken);
        LoginUser loginUser = (LoginUser) returnAuth.getPrincipal();
        String token = tokenService.createToken(loginUser);
        return ResultUtils.success(token);
    }

    @PostMapping("/login/wxWeb")
    @ApiOperation("H5微信登录")
    @ApiOperationSupport(includeParameters = {"loginVo.code", "loginVo.state"})
    public BaseResponse loginByWeb(@RequestBody LoginVo loginVo) {
        WxWebAuthentication authentication = new WxWebAuthentication(loginVo);
        Authentication returnAuth = authenticationManager.authenticate(authentication);
        LoginUser loginUser = (LoginUser) returnAuth.getPrincipal();
        String token = tokenService.createToken(loginUser);
        return ResultUtils.success(token);
    }

    /**
     * 根据code openid获取手机号
     *
     * @return
     */
    @GetMapping("/login/phone")
    @ApiOperation("小程序获取手机号")
    @Log
    public BaseResponse<String> getPhone(@Parameter(name = "code", description = "小程序code") String code) {
        log.info("【获取手机号】,code:{}", code);
        if (StringUtils.isEmpty(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String phone = wxAppService.getPhone(code);
        return ResultUtils.success(phone);
    }

    @GetMapping("/getUserInfo")
    @ApiOperation("获取当前登陆用户信息")
    @Log
    public BaseResponse getCurrentUser() {
        return ResultUtils.success(loginBusinessService.getCurrentUser());
    }
}

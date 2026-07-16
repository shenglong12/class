package com.kuafu.login.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.kuafu.common.annotation.Log;
import com.kuafu.common.cache.Cache;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.login.LoginUser;
import com.kuafu.common.login.SecurityUtils;
import com.kuafu.common.util.JSON;
import com.kuafu.common.util.StringUtils;
import com.kuafu.login.config.LoginRelevanceConfig;
import com.kuafu.login.config.LoginSmsConfig;
import com.kuafu.login.model.LoginVo;
import com.kuafu.login.provider.SmsAuthentication;
import com.kuafu.login.provider.WxAppAuthentication;
import com.kuafu.login.provider.WxAppProvider;
import com.kuafu.login.provider.WxWebAuthentication;
import com.kuafu.login.service.LoginBusinessService;
import com.kuafu.login.service.TokenService;
import com.kuafu.login.service.WechatRegisterService;
import com.kuafu.login.service.WxAppService;
import com.kuafu.login.utils.MessageTemplate;
import com.kuafu.web.config.WechatConfig;
import com.kuafu.web.dynamic.DynamicServiceInvoker;
import com.kuafu.web.dynamic.VoConverter;
import com.kuafu.web.handler.TenantContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.kuafu.login.constant.LoginConstant.LOGIN_CACHE_PRE;

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

    @Autowired
    private Cache cache;

    @Autowired
    private LoginSmsConfig loginSmsConfig;

    @Autowired
    private MessageTemplate messageTemplate;
    @Autowired
    private WechatRegisterService wechatRegisterService;

    @Autowired
    private DynamicServiceInvoker dynamicServiceInvoker;

    @Autowired
    private WxAppProvider wxAppProvider;

    @Autowired
    private WechatConfig wechatConfig;


    @GetMapping("/login/openid")
    @ApiOperation("获取小程序openid")
    public BaseResponse getOpenId(@Validated @RequestParam @NotBlank(message = "code不能为空") String code) {
        //1.根据code换取openid
        try {
            String openid = wxAppProvider.wxAppCode2Session(code);
            return ResultUtils.success(openid);
        } catch (Exception e) {

            return ResultUtils.success(null, e.getMessage());
        }


    }

    @PostMapping("/login/wxApp")
    @ApiOperation("小程序CODE登陆")
    @ApiOperationSupport(includeParameters = {"loginVo.code"})
    public BaseResponse login(@RequestBody LoginVo loginVo) {
        LoginRelevanceConfig.setLoginRelevanceTable(loginVo.getRelevanceTable());
        WxAppAuthentication authentication = new WxAppAuthentication(loginVo);
        Authentication returnAuth = authenticationManager.authenticate(authentication);
        LoginUser loginUser = (LoginUser) returnAuth.getPrincipal();
        String token = tokenService.createToken(loginUser);
        LoginRelevanceConfig.remove();
        return ResultUtils.success(token);
    }

    @PostMapping("/login/passwd")
    @ApiOperation("用户名密码登陆")
    @ApiOperationSupport(includeParameters = {"loginVo.phone", "loginVo.password"})
    public BaseResponse loginByPasswd(@RequestBody LoginVo loginVo) {
        LoginRelevanceConfig.setLoginRelevanceTable(loginVo.getRelevanceTable());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVo.getPhone(), loginVo.getPassword());
        Authentication returnAuth = authenticationManager.authenticate(authenticationToken);
        LoginUser loginUser = (LoginUser) returnAuth.getPrincipal();
        String token = tokenService.createToken(loginUser);
        if (TenantContextHolder.getEnableTenant()) {
            Integer tenantId = loginBusinessService.getUserTenantIdByLoginUser(loginUser);
            if (tenantId != null) {
                loginUser.setTenantId(tenantId);
            }
        }
        if (StringUtils.isNotEmpty(loginVo.getOpenId())) {
            final Long loginUserUserId = loginUser.getUserId();
            loginBusinessService.updateOpenIdById(loginUserUserId, loginVo.getOpenId());
        }

        LoginRelevanceConfig.remove();
        return ResultUtils.success(token);
    }

    @PostMapping("/login/wxWeb")
    @ApiOperation("H5微信登录")
    @ApiOperationSupport(includeParameters = {"loginVo.code", "loginVo.state"})
    public BaseResponse loginByWeb(@RequestBody LoginVo loginVo) {
        LoginRelevanceConfig.setLoginRelevanceTable(loginVo.getRelevanceTable());
        WxWebAuthentication authentication = new WxWebAuthentication(loginVo);
        Authentication returnAuth = authenticationManager.authenticate(authentication);
        LoginUser loginUser = (LoginUser) returnAuth.getPrincipal();
        String token = tokenService.createToken(loginUser);
        LoginRelevanceConfig.remove();
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
    public BaseResponse<String> getPhone(@Parameter(name = "code", description = "小程序code") String code,
                                         @RequestParam(name = "relevanceTable", required = false) String relevanceTable) {
        log.info("【获取手机号】,code:{}", code);
        if (StringUtils.isEmpty(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String phone = wxAppService.getPhone(code);

        if ("1".equals(wechatConfig.getIsRegister())) {
            Long loginId = wechatRegisterService.wechatRegister(phone, relevanceTable);
            log.info("【创建的用户ID】,用户ID:{}", loginId);
        }

        return ResultUtils.success(phone);
    }

    @GetMapping("/getUserInfo")
    @ApiOperation("获取当前登陆用户信息")
    @Log
    public BaseResponse getCurrentUser() {
        final Object currentUser = loginBusinessService.getCurrentUser();
        return ResultUtils.success(currentUser);
    }

    @GetMapping("/getLoginUser")
    @ApiOperation("获取当前登陆登陆用户信息（缓存中）")
    @Log
    public BaseResponse getLoginUser() {
//        final Object currentUser = loginBusinessService.getCurrentUser();
        final LoginUser loginUser = SecurityUtils.getLoginUser();
        loginUser.setToken(null);
        loginUser.setLoginTime(null);
        loginUser.setUserId(Long.valueOf(loginUser.getRelevanceId()));
        loginUser.setRelevanceTable(loginUser.getRelevanceTable());
        if (TenantContextHolder.getEnableTenant()) {
            Integer userTenantIdByLoginUser = loginBusinessService.getUserTenantIdByLoginUser(loginUser);
            System.out.println("userTenantIdByLoginUser = " + userTenantIdByLoginUser);
        }
        return ResultUtils.success(loginUser);
    }


    @GetMapping("/login/sms/code")
    @ApiOperation("发送验证码")
    @Log
    public BaseResponse sendCode(@RequestParam String phone) throws Exception {

        final String numbers = RandomUtil.randomNumbers(6);
        log.info("获取的验证码是{}", numbers);
        if (!loginSmsConfig.isDebug()) {
            messageTemplate.sendMessage(phone, numbers);
        }
        cache.setCacheObject(LOGIN_CACHE_PRE + phone, numbers, loginSmsConfig.getCodeTimeout(), TimeUnit.MINUTES);
        if (loginSmsConfig.isDebug()) {
            return ResultUtils.success(numbers);
        } else {
            return ResultUtils.success();
        }
    }

    @PostMapping("/login/sms")
    @ApiOperation("手机号+验证码登陆")
    @ApiOperationSupport(includeParameters = {"loginVo.phone", "loginVo.password"})
    public BaseResponse loginBySms(@RequestBody LoginVo loginVo) {
        LoginRelevanceConfig.setLoginRelevanceTable(loginVo.getRelevanceTable());
        SmsAuthentication authenticationToken =
                new SmsAuthentication(loginVo);
        Authentication returnAuth = authenticationManager.authenticate(authenticationToken);
        LoginUser loginUser = (LoginUser) returnAuth.getPrincipal();
        String token = tokenService.createToken(loginUser);
        LoginRelevanceConfig.remove();
        return ResultUtils.success(token);
    }

    @PostMapping("/login/register")
    public Object register(
            @RequestParam("table") String table,
            @RequestBody Map<String, Object> data) {
        try {
            Object vo = VoConverter.convert(table, data);
            return dynamicServiceInvoker.invoke(table, vo, "add");
        } catch (Exception e) {

            return ResultUtils.error(e.getMessage());
        }
    }

    /**
     * 获取微信公众号授权链接
     *
     * @return
     */
    @PostMapping("get_mp_url")
    @Log
    public BaseResponse<String> getMpUrl() {
        try {
            final String mpAppId = wechatConfig.getMpAppId();
            final String mpRedirectUri = wechatConfig.getMpRedirectUri();

            if (StringUtils.isEmpty(mpAppId) || !mpAppId.startsWith("wx")) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "mp_appid is not null");
            }

            if (StringUtils.isEmpty(mpRedirectUri) || !mpRedirectUri.startsWith("http")) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "mpRedirectUri is not null");
            }
//
            String urlTemplate = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri=" +
                    "{}&response_type=code&scope=snsapi_base&state=123421#wechat_redirect";


            final String format = StringUtils.format(urlTemplate, wechatConfig.getMpAppId(), wechatConfig.getMpRedirectUri());
            log.info("【获取公众号授权链接】,urlTemplate:{}", format);
            return ResultUtils.success(format);
        } catch (Exception e) {

            return ResultUtils.success(null, e.getMessage());
        }

    }


}

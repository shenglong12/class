package com.kuafu.common.controller;

import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.dynamic_config.service.SystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

;

@RestController
@RequestMapping("/system")
@Slf4j
@Api(value = "SysConfigController", tags = {"系统配置"})
public class SysConfigController {
    @Resource
    private SystemConfigService systemConfigService;

    @GetMapping("/setting/login")
    @ApiOperation("获取系统配置")
    public BaseResponse getConfigList() {
        return ResultUtils.success();
    }
}

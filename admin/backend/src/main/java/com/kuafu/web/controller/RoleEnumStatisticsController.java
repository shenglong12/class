package com.kuafu.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;


import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.web.entity.RoleEnum;
import com.kuafu.web.service.IRoleEnumStatisticsService;
import com.kuafu.web.entity.SelectVO;

import com.kuafu.web.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;





/**

 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/roleEnumStatistics")

public class RoleEnumStatisticsController  {

    private final IRoleEnumStatisticsService roleEnumStatisticsService;


    @PostMapping("role_enum_role_enum_id_status_statistic_d3cd406e_count")
    public BaseResponse role_enum_role_enum_id_status_statistic_d3cd406e_count(@RequestBody RoleEnum statisticVo) {
        LambdaQueryWrapper<RoleEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( roleEnumStatisticsService.role_enum_role_enum_id_status_statistic_d3cd406e_count(queryWrapper));
    }
    @PostMapping("role_enum_role_enum_id_status_statistic_dec1eb62_count")
    public BaseResponse role_enum_role_enum_id_status_statistic_dec1eb62_count(@RequestBody RoleEnum statisticVo) {
        LambdaQueryWrapper<RoleEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( roleEnumStatisticsService.role_enum_role_enum_id_status_statistic_dec1eb62_count(queryWrapper));
    }
    @PostMapping("role_enum_role_enum_id_status_statistic_b83c5d21_count")
    public BaseResponse role_enum_role_enum_id_status_statistic_b83c5d21_count(@RequestBody RoleEnum statisticVo) {
        LambdaQueryWrapper<RoleEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( roleEnumStatisticsService.role_enum_role_enum_id_status_statistic_b83c5d21_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<RoleEnum> queryWrapper, RoleEnum statisticVo){
        if(StringUtils.isNotEmpty(statisticVo.getRoleName())) {
                        queryWrapper.like(RoleEnum::getRoleName, statisticVo.getRoleName());
            }
    }



}

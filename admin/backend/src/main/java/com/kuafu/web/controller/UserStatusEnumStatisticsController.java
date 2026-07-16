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
import com.kuafu.web.entity.UserStatusEnum;
import com.kuafu.web.service.IUserStatusEnumStatisticsService;
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
@RequestMapping("/userStatusEnumStatistics")

public class UserStatusEnumStatisticsController  {

    private final IUserStatusEnumStatisticsService userStatusEnumStatisticsService;


    @PostMapping("user_status_enum_user_status_enum_id_status_statistic_250bfd65_count")
    public BaseResponse user_status_enum_user_status_enum_id_status_statistic_250bfd65_count(@RequestBody UserStatusEnum statisticVo) {
        LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userStatusEnumStatisticsService.user_status_enum_user_status_enum_id_status_statistic_250bfd65_count(queryWrapper));
    }
    @PostMapping("user_status_enum_user_status_enum_id_status_statistic_75457957_count")
    public BaseResponse user_status_enum_user_status_enum_id_status_statistic_75457957_count(@RequestBody UserStatusEnum statisticVo) {
        LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userStatusEnumStatisticsService.user_status_enum_user_status_enum_id_status_statistic_75457957_count(queryWrapper));
    }
    @PostMapping("user_status_enum_user_status_enum_id_status_statistic_1ef97a93_count")
    public BaseResponse user_status_enum_user_status_enum_id_status_statistic_1ef97a93_count(@RequestBody UserStatusEnum statisticVo) {
        LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userStatusEnumStatisticsService.user_status_enum_user_status_enum_id_status_statistic_1ef97a93_count(queryWrapper));
    }
    @PostMapping("user_status_enum_status_name_status_statistic_d1621cea_count")
    public BaseResponse user_status_enum_status_name_status_statistic_d1621cea_count(@RequestBody UserStatusEnum statisticVo) {
        LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userStatusEnumStatisticsService.user_status_enum_status_name_status_statistic_d1621cea_count(queryWrapper));
    }
    @PostMapping("user_status_enum_status_name_status_statistic_0ee48f21_count")
    public BaseResponse user_status_enum_status_name_status_statistic_0ee48f21_count(@RequestBody UserStatusEnum statisticVo) {
        LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userStatusEnumStatisticsService.user_status_enum_status_name_status_statistic_0ee48f21_count(queryWrapper));
    }
    @PostMapping("user_status_enum_status_name_status_statistic_6a2582b2_count")
    public BaseResponse user_status_enum_status_name_status_statistic_6a2582b2_count(@RequestBody UserStatusEnum statisticVo) {
        LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userStatusEnumStatisticsService.user_status_enum_status_name_status_statistic_6a2582b2_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<UserStatusEnum> queryWrapper, UserStatusEnum statisticVo){
        if(StringUtils.isNotEmpty(statisticVo.getStatusName())) {
                        queryWrapper.like(UserStatusEnum::getStatusName, statisticVo.getStatusName());
            }
    }



}

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
import com.kuafu.web.entity.UserInfo;
import com.kuafu.web.service.IUserInfoStatisticsService;
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
@RequestMapping("/userInfoStatistics")

public class UserInfoStatisticsController  {

    private final IUserInfoStatisticsService userInfoStatisticsService;


    @PostMapping("user_info_statistic_26db9414_count")
    public BaseResponse user_info_statistic_26db9414_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_statistic_26db9414_count(queryWrapper));
    }
    @PostMapping("user_info_statistic_d748e97d_count")
    public BaseResponse user_info_statistic_d748e97d_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_statistic_d748e97d_count(queryWrapper));
    }
    @PostMapping("user_info_statistic_fd86a11f_count")
    public BaseResponse user_info_statistic_fd86a11f_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_statistic_fd86a11f_count(queryWrapper));
    }
    @PostMapping("user_info_statistic_cd4888d0_count")
    public BaseResponse user_info_statistic_cd4888d0_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_statistic_cd4888d0_count(queryWrapper));
    }
    @PostMapping("user_info_statistic_97b06621_count")
    public BaseResponse user_info_statistic_97b06621_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_statistic_97b06621_count(queryWrapper));
    }
    @PostMapping("user_info_statistic_b82e02ef_count")
    public BaseResponse user_info_statistic_b82e02ef_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_statistic_b82e02ef_count(queryWrapper));
    }
    @PostMapping("user_info_role_enum_role_enum_id_1_status_statistic_17efc900_count")
    public BaseResponse user_info_role_enum_role_enum_id_1_status_statistic_17efc900_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_role_enum_role_enum_id_1_status_statistic_17efc900_count(queryWrapper));
    }
    @PostMapping("user_info_role_enum_role_enum_id_1_status_statistic_c074d54d_count")
    public BaseResponse user_info_role_enum_role_enum_id_1_status_statistic_c074d54d_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_role_enum_role_enum_id_1_status_statistic_c074d54d_count(queryWrapper));
    }
    @PostMapping("user_info_role_enum_role_enum_id_1_status_statistic_ab566b0f_count")
    public BaseResponse user_info_role_enum_role_enum_id_1_status_statistic_ab566b0f_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_role_enum_role_enum_id_1_status_statistic_ab566b0f_count(queryWrapper));
    }
    @PostMapping("user_info_user_status_enum_user_status_enum_id_1_status_statistic_b868696b_count")
    public BaseResponse user_info_user_status_enum_user_status_enum_id_1_status_statistic_b868696b_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_user_status_enum_user_status_enum_id_1_status_statistic_b868696b_count(queryWrapper));
    }
    @PostMapping("user_info_user_status_enum_user_status_enum_id_1_status_statistic_811edae4_count")
    public BaseResponse user_info_user_status_enum_user_status_enum_id_1_status_statistic_811edae4_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_user_status_enum_user_status_enum_id_1_status_statistic_811edae4_count(queryWrapper));
    }
    @PostMapping("user_info_user_status_enum_user_status_enum_id_1_status_statistic_52937bd0_count")
    public BaseResponse user_info_user_status_enum_user_status_enum_id_1_status_statistic_52937bd0_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_user_status_enum_user_status_enum_id_1_status_statistic_52937bd0_count(queryWrapper));
    }
    @PostMapping("user_info_registration_date_datetime_statistic_c6d4320e_count")
    public BaseResponse user_info_registration_date_datetime_statistic_c6d4320e_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_registration_date_datetime_statistic_c6d4320e_count(queryWrapper));
    }
    @PostMapping("user_info_registration_date_datetime_statistic_5c524202_count")
    public BaseResponse user_info_registration_date_datetime_statistic_5c524202_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_registration_date_datetime_statistic_5c524202_count(queryWrapper));
    }
    @PostMapping("user_info_registration_date_datetime_statistic_f89dba19_count")
    public BaseResponse user_info_registration_date_datetime_statistic_f89dba19_count(@RequestBody UserInfo statisticVo) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( userInfoStatisticsService.user_info_registration_date_datetime_statistic_f89dba19_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<UserInfo> queryWrapper, UserInfo statisticVo){
        if(StringUtils.isNotEmpty(statisticVo.getName())) {
                        queryWrapper.like(UserInfo::getName, statisticVo.getName());
            }
        if(StringUtils.isNotEmpty(statisticVo.getStudentId())) {
                queryWrapper.eq(UserInfo::getStudentId, statisticVo.getStudentId());
            }
        if(StringUtils.isNotEmpty(statisticVo.getPhoneNumber())) {
                queryWrapper.eq(UserInfo::getPhoneNumber, statisticVo.getPhoneNumber());
            }
        if(StringUtils.isNotEmpty(statisticVo.getCollege())) {
                queryWrapper.eq(UserInfo::getCollege, statisticVo.getCollege());
            }
        if(StringUtils.isNotEmpty(statisticVo.getMajor())) {
                queryWrapper.eq(UserInfo::getMajor, statisticVo.getMajor());
            }
        if(StringUtils.isNotEmpty(statisticVo.getPassword())) {
                queryWrapper.eq(UserInfo::getPassword, statisticVo.getPassword());
            }
            if(statisticVo.getRoleEnumRoleEnumId1() != null){
                queryWrapper.eq(UserInfo::getRoleEnumRoleEnumId1, statisticVo.getRoleEnumRoleEnumId1());
            }
            if(statisticVo.getUserStatusEnumUserStatusEnumId1() != null){
                queryWrapper.eq(UserInfo::getUserStatusEnumUserStatusEnumId1, statisticVo.getUserStatusEnumUserStatusEnumId1());
            }
            if(statisticVo.getRegistrationDate() != null){
                queryWrapper.eq(UserInfo::getRegistrationDate, statisticVo.getRegistrationDate());
            }
    }



}

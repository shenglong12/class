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
import com.kuafu.web.entity.BackgroundImage;
import com.kuafu.web.service.IBackgroundImageStatisticsService;
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
@RequestMapping("/backgroundImageStatistics")

public class BackgroundImageStatisticsController  {

    private final IBackgroundImageStatisticsService backgroundImageStatisticsService;


    @PostMapping("background_image_statistic_2cec2f0a_count")
    public BaseResponse background_image_statistic_2cec2f0a_count(@RequestBody BackgroundImage statisticVo) {
        LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( backgroundImageStatisticsService.background_image_statistic_2cec2f0a_count(queryWrapper));
    }
    @PostMapping("background_image_statistic_6d9fd8e7_count")
    public BaseResponse background_image_statistic_6d9fd8e7_count(@RequestBody BackgroundImage statisticVo) {
        LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( backgroundImageStatisticsService.background_image_statistic_6d9fd8e7_count(queryWrapper));
    }
    @PostMapping("background_image_statistic_db8eea91_count")
    public BaseResponse background_image_statistic_db8eea91_count(@RequestBody BackgroundImage statisticVo) {
        LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( backgroundImageStatisticsService.background_image_statistic_db8eea91_count(queryWrapper));
    }
    @PostMapping("background_image_upload_time_datetime_statistic_775bad3a_count")
    public BaseResponse background_image_upload_time_datetime_statistic_775bad3a_count(@RequestBody BackgroundImage statisticVo) {
        LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( backgroundImageStatisticsService.background_image_upload_time_datetime_statistic_775bad3a_count(queryWrapper));
    }
    @PostMapping("background_image_upload_time_datetime_statistic_401f3864_count")
    public BaseResponse background_image_upload_time_datetime_statistic_401f3864_count(@RequestBody BackgroundImage statisticVo) {
        LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( backgroundImageStatisticsService.background_image_upload_time_datetime_statistic_401f3864_count(queryWrapper));
    }
    @PostMapping("background_image_upload_time_datetime_statistic_f7bdec73_count")
    public BaseResponse background_image_upload_time_datetime_statistic_f7bdec73_count(@RequestBody BackgroundImage statisticVo) {
        LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( backgroundImageStatisticsService.background_image_upload_time_datetime_statistic_f7bdec73_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<BackgroundImage> queryWrapper, BackgroundImage statisticVo){
            if(statisticVo.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(BackgroundImage::getUserInfoUserInfoId1, statisticVo.getUserInfoUserInfoId1());
            }
        if(StringUtils.isNotEmpty(statisticVo.getImageFilesResourceKey())) {
                queryWrapper.eq(BackgroundImage::getImageFilesResourceKey, statisticVo.getImageFilesResourceKey());
            }
            if(statisticVo.getUploadTime() != null){
                queryWrapper.eq(BackgroundImage::getUploadTime, statisticVo.getUploadTime());
            }
    }



}

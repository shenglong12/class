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
import com.kuafu.web.entity.CourseInfo;
import com.kuafu.web.service.ICourseInfoStatisticsService;
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
@RequestMapping("/courseInfoStatistics")

public class CourseInfoStatisticsController  {

    private final ICourseInfoStatisticsService courseInfoStatisticsService;


    @PostMapping("course_info_statistic_11f06534_count")
    public BaseResponse course_info_statistic_11f06534_count(@RequestBody CourseInfo statisticVo) {
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( courseInfoStatisticsService.course_info_statistic_11f06534_count(queryWrapper));
    }
    @PostMapping("course_info_statistic_43e99b84_count")
    public BaseResponse course_info_statistic_43e99b84_count(@RequestBody CourseInfo statisticVo) {
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( courseInfoStatisticsService.course_info_statistic_43e99b84_count(queryWrapper));
    }
    @PostMapping("course_info_statistic_6ba45514_count")
    public BaseResponse course_info_statistic_6ba45514_count(@RequestBody CourseInfo statisticVo) {
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( courseInfoStatisticsService.course_info_statistic_6ba45514_count(queryWrapper));
    }
    @PostMapping("course_info_start_date_datetime_statistic_799f21ac_count")
    public BaseResponse course_info_start_date_datetime_statistic_799f21ac_count(@RequestBody CourseInfo statisticVo) {
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( courseInfoStatisticsService.course_info_start_date_datetime_statistic_799f21ac_count(queryWrapper));
    }
    @PostMapping("course_info_start_date_datetime_statistic_40a54bd0_count")
    public BaseResponse course_info_start_date_datetime_statistic_40a54bd0_count(@RequestBody CourseInfo statisticVo) {
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( courseInfoStatisticsService.course_info_start_date_datetime_statistic_40a54bd0_count(queryWrapper));
    }
    @PostMapping("course_info_start_date_datetime_statistic_470d5ddb_count")
    public BaseResponse course_info_start_date_datetime_statistic_470d5ddb_count(@RequestBody CourseInfo statisticVo) {
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( courseInfoStatisticsService.course_info_start_date_datetime_statistic_470d5ddb_count(queryWrapper));
    }
    @PostMapping("course_info_end_date_datetime_statistic_9bbd1d33_count")
    public BaseResponse course_info_end_date_datetime_statistic_9bbd1d33_count(@RequestBody CourseInfo statisticVo) {
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( courseInfoStatisticsService.course_info_end_date_datetime_statistic_9bbd1d33_count(queryWrapper));
    }
    @PostMapping("course_info_end_date_datetime_statistic_f4a5cdc7_count")
    public BaseResponse course_info_end_date_datetime_statistic_f4a5cdc7_count(@RequestBody CourseInfo statisticVo) {
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( courseInfoStatisticsService.course_info_end_date_datetime_statistic_f4a5cdc7_count(queryWrapper));
    }
    @PostMapping("course_info_end_date_datetime_statistic_9844a0b8_count")
    public BaseResponse course_info_end_date_datetime_statistic_9844a0b8_count(@RequestBody CourseInfo statisticVo) {
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( courseInfoStatisticsService.course_info_end_date_datetime_statistic_9844a0b8_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<CourseInfo> queryWrapper, CourseInfo statisticVo){
        if(StringUtils.isNotEmpty(statisticVo.getCourseName())) {
                        queryWrapper.like(CourseInfo::getCourseName, statisticVo.getCourseName());
            }
        if(StringUtils.isNotEmpty(statisticVo.getCollege())) {
                queryWrapper.eq(CourseInfo::getCollege, statisticVo.getCollege());
            }
        if(StringUtils.isNotEmpty(statisticVo.getMajor())) {
                queryWrapper.eq(CourseInfo::getMajor, statisticVo.getMajor());
            }
            if(statisticVo.getClassroomInfoClassroomInfoId1() != null){
                queryWrapper.eq(CourseInfo::getClassroomInfoClassroomInfoId1, statisticVo.getClassroomInfoClassroomInfoId1());
            }
            if(statisticVo.getStartDate() != null){
                queryWrapper.eq(CourseInfo::getStartDate, statisticVo.getStartDate());
            }
            if(statisticVo.getEndDate() != null){
                queryWrapper.eq(CourseInfo::getEndDate, statisticVo.getEndDate());
            }
        if(StringUtils.isNotEmpty(statisticVo.getWeekdays())) {
                queryWrapper.eq(CourseInfo::getWeekdays, statisticVo.getWeekdays());
            }
        if(StringUtils.isNotEmpty(statisticVo.getTimePeriod())) {
                queryWrapper.eq(CourseInfo::getTimePeriod, statisticVo.getTimePeriod());
            }
        if(StringUtils.isNotEmpty(statisticVo.getContent())) {
                queryWrapper.eq(CourseInfo::getContent, statisticVo.getContent());
            }
    }



}

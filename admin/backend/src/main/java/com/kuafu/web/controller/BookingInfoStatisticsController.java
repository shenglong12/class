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
import com.kuafu.web.entity.BookingInfo;
import com.kuafu.web.service.IBookingInfoStatisticsService;
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
@RequestMapping("/bookingInfoStatistics")

public class BookingInfoStatisticsController  {

    private final IBookingInfoStatisticsService bookingInfoStatisticsService;


    @PostMapping("booking_info_statistic_1126e6b6_count")
    public BaseResponse booking_info_statistic_1126e6b6_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_statistic_1126e6b6_count(queryWrapper));
    }
    @PostMapping("booking_info_statistic_4043b0cb_count")
    public BaseResponse booking_info_statistic_4043b0cb_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_statistic_4043b0cb_count(queryWrapper));
    }
    @PostMapping("booking_info_statistic_1853f55c_count")
    public BaseResponse booking_info_statistic_1853f55c_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_statistic_1853f55c_count(queryWrapper));
    }
    @PostMapping("booking_info_statistic_d40776f0_count")
    public BaseResponse booking_info_statistic_d40776f0_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_statistic_d40776f0_count(queryWrapper));
    }
    @PostMapping("booking_info_statistic_548dd610_count")
    public BaseResponse booking_info_statistic_548dd610_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_statistic_548dd610_count(queryWrapper));
    }
    @PostMapping("booking_info_statistic_44542338_count")
    public BaseResponse booking_info_statistic_44542338_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_statistic_44542338_count(queryWrapper));
    }
    @PostMapping("booking_info_statistic_aff5634d_count")
    public BaseResponse booking_info_statistic_aff5634d_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_statistic_aff5634d_count(queryWrapper));
    }
    @PostMapping("booking_info_statistic_17a6caf6_count")
    public BaseResponse booking_info_statistic_17a6caf6_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_statistic_17a6caf6_count(queryWrapper));
    }
    @PostMapping("booking_info_statistic_c980a12e_count")
    public BaseResponse booking_info_statistic_c980a12e_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_statistic_c980a12e_count(queryWrapper));
    }
    @PostMapping("booking_info_booking_date_datetime_statistic_eae3f8e9_count")
    public BaseResponse booking_info_booking_date_datetime_statistic_eae3f8e9_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_booking_date_datetime_statistic_eae3f8e9_count(queryWrapper));
    }
    @PostMapping("booking_info_booking_date_datetime_statistic_e87a89ae_count")
    public BaseResponse booking_info_booking_date_datetime_statistic_e87a89ae_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_booking_date_datetime_statistic_e87a89ae_count(queryWrapper));
    }
    @PostMapping("booking_info_booking_date_datetime_statistic_674e8a71_count")
    public BaseResponse booking_info_booking_date_datetime_statistic_674e8a71_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_booking_date_datetime_statistic_674e8a71_count(queryWrapper));
    }
    @PostMapping("booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_e8bf0f4a_count")
    public BaseResponse booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_e8bf0f4a_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_e8bf0f4a_count(queryWrapper));
    }
    @PostMapping("booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_3920cb9d_count")
    public BaseResponse booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_3920cb9d_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_3920cb9d_count(queryWrapper));
    }
    @PostMapping("booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_abe486bb_count")
    public BaseResponse booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_abe486bb_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_abe486bb_count(queryWrapper));
    }
    @PostMapping("booking_info_create_time_datetime_statistic_deef36d9_count")
    public BaseResponse booking_info_create_time_datetime_statistic_deef36d9_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_create_time_datetime_statistic_deef36d9_count(queryWrapper));
    }
    @PostMapping("booking_info_create_time_datetime_statistic_198072fd_count")
    public BaseResponse booking_info_create_time_datetime_statistic_198072fd_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_create_time_datetime_statistic_198072fd_count(queryWrapper));
    }
    @PostMapping("booking_info_create_time_datetime_statistic_25bff35e_count")
    public BaseResponse booking_info_create_time_datetime_statistic_25bff35e_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_create_time_datetime_statistic_25bff35e_count(queryWrapper));
    }
    @PostMapping("booking_info_start_time_datetime_statistic_f9865a97_count")
    public BaseResponse booking_info_start_time_datetime_statistic_f9865a97_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_start_time_datetime_statistic_f9865a97_count(queryWrapper));
    }
    @PostMapping("booking_info_start_time_datetime_statistic_8d1d71fe_count")
    public BaseResponse booking_info_start_time_datetime_statistic_8d1d71fe_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_start_time_datetime_statistic_8d1d71fe_count(queryWrapper));
    }
    @PostMapping("booking_info_start_time_datetime_statistic_937c1379_count")
    public BaseResponse booking_info_start_time_datetime_statistic_937c1379_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_start_time_datetime_statistic_937c1379_count(queryWrapper));
    }
    @PostMapping("booking_info_end_time_datetime_statistic_2492f29a_count")
    public BaseResponse booking_info_end_time_datetime_statistic_2492f29a_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_end_time_datetime_statistic_2492f29a_count(queryWrapper));
    }
    @PostMapping("booking_info_end_time_datetime_statistic_e6adae60_count")
    public BaseResponse booking_info_end_time_datetime_statistic_e6adae60_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_end_time_datetime_statistic_e6adae60_count(queryWrapper));
    }
    @PostMapping("booking_info_end_time_datetime_statistic_d22436f5_count")
    public BaseResponse booking_info_end_time_datetime_statistic_d22436f5_count(@RequestBody BookingInfo statisticVo) {
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingInfoStatisticsService.booking_info_end_time_datetime_statistic_d22436f5_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<BookingInfo> queryWrapper, BookingInfo statisticVo){
            if(statisticVo.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(BookingInfo::getUserInfoUserInfoId1, statisticVo.getUserInfoUserInfoId1());
            }
            if(statisticVo.getClassroomInfoClassroomInfoId1() != null){
                queryWrapper.eq(BookingInfo::getClassroomInfoClassroomInfoId1, statisticVo.getClassroomInfoClassroomInfoId1());
            }
            if(statisticVo.getBookingDate() != null){
                queryWrapper.eq(BookingInfo::getBookingDate, statisticVo.getBookingDate());
            }
        if(StringUtils.isNotEmpty(statisticVo.getTimePeriod())) {
                queryWrapper.eq(BookingInfo::getTimePeriod, statisticVo.getTimePeriod());
            }
        if(StringUtils.isNotEmpty(statisticVo.getPurpose())) {
                queryWrapper.eq(BookingInfo::getPurpose, statisticVo.getPurpose());
            }
            if(statisticVo.getBookingStatusEnumBookingStatusEnumId1() != null){
                queryWrapper.eq(BookingInfo::getBookingStatusEnumBookingStatusEnumId1, statisticVo.getBookingStatusEnumBookingStatusEnumId1());
            }
            if(statisticVo.getCreateTime() != null){
                queryWrapper.eq(BookingInfo::getCreateTime, statisticVo.getCreateTime());
            }
        if(StringUtils.isNotEmpty(statisticVo.getStartTime())) {
                queryWrapper.eq(BookingInfo::getStartTime, statisticVo.getStartTime());
            }
        if(StringUtils.isNotEmpty(statisticVo.getEndTime())) {
                queryWrapper.eq(BookingInfo::getEndTime, statisticVo.getEndTime());
            }
    }



}

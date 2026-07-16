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
import com.kuafu.web.entity.BookingStatusEnum;
import com.kuafu.web.service.IBookingStatusEnumStatisticsService;
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
@RequestMapping("/bookingStatusEnumStatistics")

public class BookingStatusEnumStatisticsController  {

    private final IBookingStatusEnumStatisticsService bookingStatusEnumStatisticsService;


    @PostMapping("booking_status_enum_booking_status_enum_id_status_statistic_adcce722_count")
    public BaseResponse booking_status_enum_booking_status_enum_id_status_statistic_adcce722_count(@RequestBody BookingStatusEnum statisticVo) {
        LambdaQueryWrapper<BookingStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingStatusEnumStatisticsService.booking_status_enum_booking_status_enum_id_status_statistic_adcce722_count(queryWrapper));
    }
    @PostMapping("booking_status_enum_booking_status_enum_id_status_statistic_e5e0d2ef_count")
    public BaseResponse booking_status_enum_booking_status_enum_id_status_statistic_e5e0d2ef_count(@RequestBody BookingStatusEnum statisticVo) {
        LambdaQueryWrapper<BookingStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingStatusEnumStatisticsService.booking_status_enum_booking_status_enum_id_status_statistic_e5e0d2ef_count(queryWrapper));
    }
    @PostMapping("booking_status_enum_booking_status_enum_id_status_statistic_b6fdee33_count")
    public BaseResponse booking_status_enum_booking_status_enum_id_status_statistic_b6fdee33_count(@RequestBody BookingStatusEnum statisticVo) {
        LambdaQueryWrapper<BookingStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingStatusEnumStatisticsService.booking_status_enum_booking_status_enum_id_status_statistic_b6fdee33_count(queryWrapper));
    }
    @PostMapping("booking_status_enum_status_name_status_statistic_90b34348_count")
    public BaseResponse booking_status_enum_status_name_status_statistic_90b34348_count(@RequestBody BookingStatusEnum statisticVo) {
        LambdaQueryWrapper<BookingStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingStatusEnumStatisticsService.booking_status_enum_status_name_status_statistic_90b34348_count(queryWrapper));
    }
    @PostMapping("booking_status_enum_status_name_status_statistic_6038f1fd_count")
    public BaseResponse booking_status_enum_status_name_status_statistic_6038f1fd_count(@RequestBody BookingStatusEnum statisticVo) {
        LambdaQueryWrapper<BookingStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingStatusEnumStatisticsService.booking_status_enum_status_name_status_statistic_6038f1fd_count(queryWrapper));
    }
    @PostMapping("booking_status_enum_status_name_status_statistic_a52c379f_count")
    public BaseResponse booking_status_enum_status_name_status_statistic_a52c379f_count(@RequestBody BookingStatusEnum statisticVo) {
        LambdaQueryWrapper<BookingStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( bookingStatusEnumStatisticsService.booking_status_enum_status_name_status_statistic_a52c379f_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<BookingStatusEnum> queryWrapper, BookingStatusEnum statisticVo){
        if(StringUtils.isNotEmpty(statisticVo.getStatusName())) {
                        queryWrapper.like(BookingStatusEnum::getStatusName, statisticVo.getStatusName());
            }
    }



}

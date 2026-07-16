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
import com.kuafu.web.entity.ScanRecord;
import com.kuafu.web.service.IScanRecordStatisticsService;
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
@RequestMapping("/scanRecordStatistics")

public class ScanRecordStatisticsController  {

    private final IScanRecordStatisticsService scanRecordStatisticsService;


    @PostMapping("scan_record_statistic_cf42143d_count")
    public BaseResponse scan_record_statistic_cf42143d_count(@RequestBody ScanRecord statisticVo) {
        LambdaQueryWrapper<ScanRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( scanRecordStatisticsService.scan_record_statistic_cf42143d_count(queryWrapper));
    }
    @PostMapping("scan_record_statistic_53e9c652_count")
    public BaseResponse scan_record_statistic_53e9c652_count(@RequestBody ScanRecord statisticVo) {
        LambdaQueryWrapper<ScanRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( scanRecordStatisticsService.scan_record_statistic_53e9c652_count(queryWrapper));
    }
    @PostMapping("scan_record_statistic_44e4ab34_count")
    public BaseResponse scan_record_statistic_44e4ab34_count(@RequestBody ScanRecord statisticVo) {
        LambdaQueryWrapper<ScanRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( scanRecordStatisticsService.scan_record_statistic_44e4ab34_count(queryWrapper));
    }
    @PostMapping("scan_record_scan_time_datetime_statistic_2603ead4_count")
    public BaseResponse scan_record_scan_time_datetime_statistic_2603ead4_count(@RequestBody ScanRecord statisticVo) {
        LambdaQueryWrapper<ScanRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( scanRecordStatisticsService.scan_record_scan_time_datetime_statistic_2603ead4_count(queryWrapper));
    }
    @PostMapping("scan_record_scan_time_datetime_statistic_c280d87a_count")
    public BaseResponse scan_record_scan_time_datetime_statistic_c280d87a_count(@RequestBody ScanRecord statisticVo) {
        LambdaQueryWrapper<ScanRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( scanRecordStatisticsService.scan_record_scan_time_datetime_statistic_c280d87a_count(queryWrapper));
    }
    @PostMapping("scan_record_scan_time_datetime_statistic_e3554bf9_count")
    public BaseResponse scan_record_scan_time_datetime_statistic_e3554bf9_count(@RequestBody ScanRecord statisticVo) {
        LambdaQueryWrapper<ScanRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( scanRecordStatisticsService.scan_record_scan_time_datetime_statistic_e3554bf9_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<ScanRecord> queryWrapper, ScanRecord statisticVo){
            if(statisticVo.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(ScanRecord::getUserInfoUserInfoId1, statisticVo.getUserInfoUserInfoId1());
            }
        if(StringUtils.isNotEmpty(statisticVo.getBuildingName())) {
                        queryWrapper.like(ScanRecord::getBuildingName, statisticVo.getBuildingName());
            }
        if(StringUtils.isNotEmpty(statisticVo.getRoomNumber())) {
                queryWrapper.eq(ScanRecord::getRoomNumber, statisticVo.getRoomNumber());
            }
            if(statisticVo.getScanTime() != null){
                queryWrapper.eq(ScanRecord::getScanTime, statisticVo.getScanTime());
            }
    }



}

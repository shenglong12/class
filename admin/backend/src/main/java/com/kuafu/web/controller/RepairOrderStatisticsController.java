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
import com.kuafu.web.entity.RepairOrder;
import com.kuafu.web.service.IRepairOrderStatisticsService;
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
@RequestMapping("/repairOrderStatistics")

public class RepairOrderStatisticsController  {

    private final IRepairOrderStatisticsService repairOrderStatisticsService;


    @PostMapping("repair_order_statistic_6e1b2cda_count")
    public BaseResponse repair_order_statistic_6e1b2cda_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_statistic_6e1b2cda_count(queryWrapper));
    }
    @PostMapping("repair_order_statistic_706e6dad_count")
    public BaseResponse repair_order_statistic_706e6dad_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_statistic_706e6dad_count(queryWrapper));
    }
    @PostMapping("repair_order_statistic_dbd88f7e_count")
    public BaseResponse repair_order_statistic_dbd88f7e_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_statistic_dbd88f7e_count(queryWrapper));
    }
    @PostMapping("repair_order_statistic_f027c1c8_count")
    public BaseResponse repair_order_statistic_f027c1c8_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_statistic_f027c1c8_count(queryWrapper));
    }
    @PostMapping("repair_order_statistic_6a0412be_count")
    public BaseResponse repair_order_statistic_6a0412be_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_statistic_6a0412be_count(queryWrapper));
    }
    @PostMapping("repair_order_statistic_ab5cf58a_count")
    public BaseResponse repair_order_statistic_ab5cf58a_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_statistic_ab5cf58a_count(queryWrapper));
    }
    @PostMapping("repair_order_statistic_e0ad78fb_count")
    public BaseResponse repair_order_statistic_e0ad78fb_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_statistic_e0ad78fb_count(queryWrapper));
    }
    @PostMapping("repair_order_statistic_def7eca8_count")
    public BaseResponse repair_order_statistic_def7eca8_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_statistic_def7eca8_count(queryWrapper));
    }
    @PostMapping("repair_order_statistic_e9136ad8_count")
    public BaseResponse repair_order_statistic_e9136ad8_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_statistic_e9136ad8_count(queryWrapper));
    }
    @PostMapping("repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_4de288b4_count")
    public BaseResponse repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_4de288b4_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_4de288b4_count(queryWrapper));
    }
    @PostMapping("repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_86902e9b_count")
    public BaseResponse repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_86902e9b_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_86902e9b_count(queryWrapper));
    }
    @PostMapping("repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_ae2d02e5_count")
    public BaseResponse repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_ae2d02e5_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_ae2d02e5_count(queryWrapper));
    }
    @PostMapping("repair_order_create_time_datetime_statistic_426c9cba_count")
    public BaseResponse repair_order_create_time_datetime_statistic_426c9cba_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_create_time_datetime_statistic_426c9cba_count(queryWrapper));
    }
    @PostMapping("repair_order_create_time_datetime_statistic_13a3f358_count")
    public BaseResponse repair_order_create_time_datetime_statistic_13a3f358_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_create_time_datetime_statistic_13a3f358_count(queryWrapper));
    }
    @PostMapping("repair_order_create_time_datetime_statistic_b1da4bdf_count")
    public BaseResponse repair_order_create_time_datetime_statistic_b1da4bdf_count(@RequestBody RepairOrder statisticVo) {
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairOrderStatisticsService.repair_order_create_time_datetime_statistic_b1da4bdf_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<RepairOrder> queryWrapper, RepairOrder statisticVo){
            if(statisticVo.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(RepairOrder::getUserInfoUserInfoId1, statisticVo.getUserInfoUserInfoId1());
            }
            if(statisticVo.getClassroomInfoClassroomInfoId1() != null){
                queryWrapper.eq(RepairOrder::getClassroomInfoClassroomInfoId1, statisticVo.getClassroomInfoClassroomInfoId1());
            }
        if(StringUtils.isNotEmpty(statisticVo.getContent())) {
                queryWrapper.eq(RepairOrder::getContent, statisticVo.getContent());
            }
            if(statisticVo.getRepairStatusEnumRepairStatusEnumId1() != null){
                queryWrapper.eq(RepairOrder::getRepairStatusEnumRepairStatusEnumId1, statisticVo.getRepairStatusEnumRepairStatusEnumId1());
            }
        if(StringUtils.isNotEmpty(statisticVo.getFeedback())) {
                queryWrapper.eq(RepairOrder::getFeedback, statisticVo.getFeedback());
            }
            if(statisticVo.getCreateTime() != null){
                queryWrapper.eq(RepairOrder::getCreateTime, statisticVo.getCreateTime());
            }
        if(StringUtils.isNotEmpty(statisticVo.getImageFilesResourceKey())) {
                queryWrapper.eq(RepairOrder::getImageFilesResourceKey, statisticVo.getImageFilesResourceKey());
            }
    }



}

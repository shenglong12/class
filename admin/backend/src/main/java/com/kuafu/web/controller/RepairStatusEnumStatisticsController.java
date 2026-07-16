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
import com.kuafu.web.entity.RepairStatusEnum;
import com.kuafu.web.service.IRepairStatusEnumStatisticsService;
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
@RequestMapping("/repairStatusEnumStatistics")

public class RepairStatusEnumStatisticsController  {

    private final IRepairStatusEnumStatisticsService repairStatusEnumStatisticsService;


    @PostMapping("repair_status_enum_repair_status_enum_id_status_statistic_3331a20c_count")
    public BaseResponse repair_status_enum_repair_status_enum_id_status_statistic_3331a20c_count(@RequestBody RepairStatusEnum statisticVo) {
        LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairStatusEnumStatisticsService.repair_status_enum_repair_status_enum_id_status_statistic_3331a20c_count(queryWrapper));
    }
    @PostMapping("repair_status_enum_repair_status_enum_id_status_statistic_f7fab3d8_count")
    public BaseResponse repair_status_enum_repair_status_enum_id_status_statistic_f7fab3d8_count(@RequestBody RepairStatusEnum statisticVo) {
        LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairStatusEnumStatisticsService.repair_status_enum_repair_status_enum_id_status_statistic_f7fab3d8_count(queryWrapper));
    }
    @PostMapping("repair_status_enum_repair_status_enum_id_status_statistic_20ddc808_count")
    public BaseResponse repair_status_enum_repair_status_enum_id_status_statistic_20ddc808_count(@RequestBody RepairStatusEnum statisticVo) {
        LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairStatusEnumStatisticsService.repair_status_enum_repair_status_enum_id_status_statistic_20ddc808_count(queryWrapper));
    }
    @PostMapping("repair_status_enum_status_name_status_statistic_e2385888_count")
    public BaseResponse repair_status_enum_status_name_status_statistic_e2385888_count(@RequestBody RepairStatusEnum statisticVo) {
        LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairStatusEnumStatisticsService.repair_status_enum_status_name_status_statistic_e2385888_count(queryWrapper));
    }
    @PostMapping("repair_status_enum_status_name_status_statistic_b8607948_count")
    public BaseResponse repair_status_enum_status_name_status_statistic_b8607948_count(@RequestBody RepairStatusEnum statisticVo) {
        LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairStatusEnumStatisticsService.repair_status_enum_status_name_status_statistic_b8607948_count(queryWrapper));
    }
    @PostMapping("repair_status_enum_status_name_status_statistic_5735d9aa_count")
    public BaseResponse repair_status_enum_status_name_status_statistic_5735d9aa_count(@RequestBody RepairStatusEnum statisticVo) {
        LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( repairStatusEnumStatisticsService.repair_status_enum_status_name_status_statistic_5735d9aa_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<RepairStatusEnum> queryWrapper, RepairStatusEnum statisticVo){
        if(StringUtils.isNotEmpty(statisticVo.getStatusName())) {
                        queryWrapper.like(RepairStatusEnum::getStatusName, statisticVo.getStatusName());
            }
    }



}

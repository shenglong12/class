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
import com.kuafu.web.entity.BuildingEnum;
import com.kuafu.web.service.IBuildingEnumStatisticsService;
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
@RequestMapping("/buildingEnumStatistics")

public class BuildingEnumStatisticsController  {

    private final IBuildingEnumStatisticsService buildingEnumStatisticsService;


    @PostMapping("building_enum_building_enum_id_status_statistic_146915fe_count")
    public BaseResponse building_enum_building_enum_id_status_statistic_146915fe_count(@RequestBody BuildingEnum statisticVo) {
        LambdaQueryWrapper<BuildingEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( buildingEnumStatisticsService.building_enum_building_enum_id_status_statistic_146915fe_count(queryWrapper));
    }
    @PostMapping("building_enum_building_enum_id_status_statistic_060920a4_count")
    public BaseResponse building_enum_building_enum_id_status_statistic_060920a4_count(@RequestBody BuildingEnum statisticVo) {
        LambdaQueryWrapper<BuildingEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( buildingEnumStatisticsService.building_enum_building_enum_id_status_statistic_060920a4_count(queryWrapper));
    }
    @PostMapping("building_enum_building_enum_id_status_statistic_f0e4acc5_count")
    public BaseResponse building_enum_building_enum_id_status_statistic_f0e4acc5_count(@RequestBody BuildingEnum statisticVo) {
        LambdaQueryWrapper<BuildingEnum> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( buildingEnumStatisticsService.building_enum_building_enum_id_status_statistic_f0e4acc5_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<BuildingEnum> queryWrapper, BuildingEnum statisticVo){
        if(StringUtils.isNotEmpty(statisticVo.getBuildingName())) {
                        queryWrapper.like(BuildingEnum::getBuildingName, statisticVo.getBuildingName());
            }
    }



}

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
import com.kuafu.web.entity.ClassroomInfo;
import com.kuafu.web.service.IClassroomInfoStatisticsService;
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
@RequestMapping("/classroomInfoStatistics")

public class ClassroomInfoStatisticsController  {

    private final IClassroomInfoStatisticsService classroomInfoStatisticsService;


    @PostMapping("classroom_info_statistic_b88cfabc_count")
    public BaseResponse classroom_info_statistic_b88cfabc_count(@RequestBody ClassroomInfo statisticVo) {
        LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( classroomInfoStatisticsService.classroom_info_statistic_b88cfabc_count(queryWrapper));
    }
    @PostMapping("classroom_info_statistic_ca7982e5_count")
    public BaseResponse classroom_info_statistic_ca7982e5_count(@RequestBody ClassroomInfo statisticVo) {
        LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( classroomInfoStatisticsService.classroom_info_statistic_ca7982e5_count(queryWrapper));
    }
    @PostMapping("classroom_info_statistic_b73aeebe_count")
    public BaseResponse classroom_info_statistic_b73aeebe_count(@RequestBody ClassroomInfo statisticVo) {
        LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( classroomInfoStatisticsService.classroom_info_statistic_b73aeebe_count(queryWrapper));
    }
    @PostMapping("classroom_info_building_enum_building_enum_id_1_status_statistic_85cdeae2_count")
    public BaseResponse classroom_info_building_enum_building_enum_id_1_status_statistic_85cdeae2_count(@RequestBody ClassroomInfo statisticVo) {
        LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( classroomInfoStatisticsService.classroom_info_building_enum_building_enum_id_1_status_statistic_85cdeae2_count(queryWrapper));
    }
    @PostMapping("classroom_info_building_enum_building_enum_id_1_status_statistic_69e0173b_count")
    public BaseResponse classroom_info_building_enum_building_enum_id_1_status_statistic_69e0173b_count(@RequestBody ClassroomInfo statisticVo) {
        LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( classroomInfoStatisticsService.classroom_info_building_enum_building_enum_id_1_status_statistic_69e0173b_count(queryWrapper));
    }
    @PostMapping("classroom_info_building_enum_building_enum_id_1_status_statistic_70a96f89_count")
    public BaseResponse classroom_info_building_enum_building_enum_id_1_status_statistic_70a96f89_count(@RequestBody ClassroomInfo statisticVo) {
        LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapperParams(queryWrapper, statisticVo);

                    
        return ResultUtils.success( classroomInfoStatisticsService.classroom_info_building_enum_building_enum_id_1_status_statistic_70a96f89_count(queryWrapper));
    }


    private void queryWrapperParams(LambdaQueryWrapper<ClassroomInfo> queryWrapper, ClassroomInfo statisticVo){
            if(statisticVo.getBuildingEnumBuildingEnumId1() != null){
                queryWrapper.eq(ClassroomInfo::getBuildingEnumBuildingEnumId1, statisticVo.getBuildingEnumBuildingEnumId1());
            }
        if(StringUtils.isNotEmpty(statisticVo.getRoomNumber())) {
                queryWrapper.eq(ClassroomInfo::getRoomNumber, statisticVo.getRoomNumber());
            }
        if(StringUtils.isNotEmpty(statisticVo.getQrCodePath())) {
                queryWrapper.eq(ClassroomInfo::getQrCodePath, statisticVo.getQrCodePath());
            }
    }



}

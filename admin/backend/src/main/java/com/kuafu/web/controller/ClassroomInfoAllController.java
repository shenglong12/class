package com.kuafu.web.controller;

import com.kuafu.common.util.poi.ExcelUtil;
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
import javax.servlet.http.HttpServletResponse;


import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.web.entity.SelectVO;
import com.kuafu.web.entity.ClassroomInfoAll;
import com.kuafu.web.service.IClassroomInfoAllService;
import com.kuafu.web.vo.ClassroomInfoAllPageVO;

import com.kuafu.web.service.IBuildingEnumService;
import com.kuafu.web.entity.BuildingEnum;


/**
 * <p> 教室信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/classroomInfoAll")
@Api(tags = {"教室信息"})
public class ClassroomInfoAllController  {

    private final IClassroomInfoAllService classroomInfoAllService;

    @PostMapping("page")
    @ApiOperation("全字段分页")
    public BaseResponse page(@RequestBody ClassroomInfoAllPageVO pageVO){
        IPage<ClassroomInfoAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<ClassroomInfoAll> queryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotEmpty(pageVO.getBuildingName())) {
            queryWrapper.like(ClassroomInfoAll::getBuildingName, pageVO.getBuildingName());
        }
            if(pageVO.getBuildingEnumBuildingEnumId1() != null){
            queryWrapper.eq(ClassroomInfoAll::getBuildingEnumBuildingEnumId1, pageVO.getBuildingEnumBuildingEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
            queryWrapper.eq(ClassroomInfoAll::getRoomNumber, pageVO.getRoomNumber());
        }
        if(StringUtils.isNotEmpty(pageVO.getQrCodePath())) {
            queryWrapper.eq(ClassroomInfoAll::getQrCodePath, pageVO.getQrCodePath());
        }
        return ResultUtils.success(classroomInfoAllService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("全字段列表")
    public BaseResponse list(@RequestBody ClassroomInfoAllPageVO vo){
        LambdaQueryWrapper<ClassroomInfoAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getBuildingName())) {
            queryWrapper.like(ClassroomInfoAll::getBuildingName, vo.getBuildingName());
        }
                if(vo.getBuildingEnumBuildingEnumId1() != null){
            queryWrapper.eq(ClassroomInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
            queryWrapper.eq(ClassroomInfoAll::getRoomNumber, vo.getRoomNumber());
        }
            if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
            queryWrapper.eq(ClassroomInfoAll::getQrCodePath, vo.getQrCodePath());
        }
        return ResultUtils.success(classroomInfoAllService.list(queryWrapper));
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer classroomInfoId) {
        ClassroomInfoAll entity = this.classroomInfoAllService.getById(classroomInfoId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


        private final IBuildingEnumService buildingEnumService;

        @GetMapping("get/building_enum_list")
        @ApiOperation("查询下拉框的外键信息")
        public BaseResponse get_building_enum_list() {
            List<BuildingEnum> list = this.buildingEnumService.list();
            final List<SelectVO> selectVoList = list.stream().map(p ->
                new SelectVO(p.getBuildingEnumId(), p.getBuildingName().toString()))
                        .collect(Collectors.toList());
            return ResultUtils.success(selectVoList);
        }


        /**
         * 导出excel
         */
        @PostMapping("/export")
        public void export(HttpServletResponse response, @RequestBody ClassroomInfoAllPageVO vo) {

            LambdaQueryWrapper<ClassroomInfoAll> queryWrapper = new LambdaQueryWrapper<>();

                if(StringUtils.isNotEmpty(vo.getBuildingName())) {
                                queryWrapper.like(ClassroomInfoAll::getBuildingName, vo.getBuildingName());
                    }
                    if(vo.getBuildingEnumBuildingEnumId1() != null){
                        queryWrapper.eq(ClassroomInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                    }
                if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                        queryWrapper.eq(ClassroomInfoAll::getRoomNumber, vo.getRoomNumber());
                    }
                if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
                        queryWrapper.eq(ClassroomInfoAll::getQrCodePath, vo.getQrCodePath());
                    }
            List<ClassroomInfoAll> list = classroomInfoAllService.list(queryWrapper);
            ExcelUtil<ClassroomInfoAll> util = new ExcelUtil<>(ClassroomInfoAll.class);
            util.exportExcel(response, list, "数据");
        }


}

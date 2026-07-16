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
import com.kuafu.web.entity.CourseInfoAll;
import com.kuafu.web.service.ICourseInfoAllService;
import com.kuafu.web.vo.CourseInfoAllPageVO;

import com.kuafu.web.service.IClassroomInfoService;
import com.kuafu.web.entity.ClassroomInfo;


/**
 * <p> 课程信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/courseInfoAll")
@Api(tags = {"课程信息"})
public class CourseInfoAllController  {

    private final ICourseInfoAllService courseInfoAllService;

    @PostMapping("page")
    @ApiOperation("全字段分页")
    public BaseResponse page(@RequestBody CourseInfoAllPageVO pageVO){
        IPage<CourseInfoAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<CourseInfoAll> queryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
            queryWrapper.eq(CourseInfoAll::getCollege, pageVO.getCollege());
        }
        if(StringUtils.isNotEmpty(pageVO.getCourseName())) {
            queryWrapper.like(CourseInfoAll::getCourseName, pageVO.getCourseName());
        }
        if(StringUtils.isNotEmpty(pageVO.getWeekdays())) {
            queryWrapper.eq(CourseInfoAll::getWeekdays, pageVO.getWeekdays());
        }
        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
            queryWrapper.eq(CourseInfoAll::getRoomNumber, pageVO.getRoomNumber());
        }
        if(StringUtils.isNotEmpty(pageVO.getQrCodePath())) {
            queryWrapper.eq(CourseInfoAll::getQrCodePath, pageVO.getQrCodePath());
        }
            if(pageVO.getStartDate() != null){
            queryWrapper.eq(CourseInfoAll::getStartDate, pageVO.getStartDate());
        }
        if(StringUtils.isNotEmpty(pageVO.getContent())) {
            queryWrapper.eq(CourseInfoAll::getContent, pageVO.getContent());
        }
            if(pageVO.getEndDate() != null){
            queryWrapper.eq(CourseInfoAll::getEndDate, pageVO.getEndDate());
        }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
            queryWrapper.eq(CourseInfoAll::getMajor, pageVO.getMajor());
        }
            if(pageVO.getBuildingEnumBuildingEnumId1() != null){
            queryWrapper.eq(CourseInfoAll::getBuildingEnumBuildingEnumId1, pageVO.getBuildingEnumBuildingEnumId1());
        }
            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(CourseInfoAll::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getTimePeriod())) {
            queryWrapper.eq(CourseInfoAll::getTimePeriod, pageVO.getTimePeriod());
        }
        return ResultUtils.success(courseInfoAllService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("全字段列表")
    public BaseResponse list(@RequestBody CourseInfoAllPageVO vo){
        LambdaQueryWrapper<CourseInfoAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCollege())) {
            queryWrapper.eq(CourseInfoAll::getCollege, vo.getCollege());
        }
            if(StringUtils.isNotEmpty(vo.getCourseName())) {
            queryWrapper.like(CourseInfoAll::getCourseName, vo.getCourseName());
        }
            if(StringUtils.isNotEmpty(vo.getWeekdays())) {
            queryWrapper.eq(CourseInfoAll::getWeekdays, vo.getWeekdays());
        }
            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
            queryWrapper.eq(CourseInfoAll::getRoomNumber, vo.getRoomNumber());
        }
            if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
            queryWrapper.eq(CourseInfoAll::getQrCodePath, vo.getQrCodePath());
        }
                if(vo.getStartDate() != null){
            queryWrapper.eq(CourseInfoAll::getStartDate, vo.getStartDate());
        }
            if(StringUtils.isNotEmpty(vo.getContent())) {
            queryWrapper.eq(CourseInfoAll::getContent, vo.getContent());
        }
                if(vo.getEndDate() != null){
            queryWrapper.eq(CourseInfoAll::getEndDate, vo.getEndDate());
        }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
            queryWrapper.eq(CourseInfoAll::getMajor, vo.getMajor());
        }
                if(vo.getBuildingEnumBuildingEnumId1() != null){
            queryWrapper.eq(CourseInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
        }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(CourseInfoAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
        }
            if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
            queryWrapper.eq(CourseInfoAll::getTimePeriod, vo.getTimePeriod());
        }
        return ResultUtils.success(courseInfoAllService.list(queryWrapper));
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer courseInfoId) {
        CourseInfoAll entity = this.courseInfoAllService.getById(courseInfoId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


        private final IClassroomInfoService classroomInfoService;

        @GetMapping("get/classroom_info_list")
        @ApiOperation("查询下拉框的外键信息")
        public BaseResponse get_classroom_info_list() {
            List<ClassroomInfo> list = this.classroomInfoService.list();
            final List<SelectVO> selectVoList = list.stream().map(p ->
                new SelectVO(p.getClassroomInfoId(), p.getRoomNumber().toString()))
                        .collect(Collectors.toList());
            return ResultUtils.success(selectVoList);
        }


        /**
         * 导出excel
         */
        @PostMapping("/export")
        public void export(HttpServletResponse response, @RequestBody CourseInfoAllPageVO vo) {

            LambdaQueryWrapper<CourseInfoAll> queryWrapper = new LambdaQueryWrapper<>();

                if(StringUtils.isNotEmpty(vo.getCollege())) {
                        queryWrapper.eq(CourseInfoAll::getCollege, vo.getCollege());
                    }
                if(StringUtils.isNotEmpty(vo.getCourseName())) {
                                queryWrapper.like(CourseInfoAll::getCourseName, vo.getCourseName());
                    }
                if(StringUtils.isNotEmpty(vo.getWeekdays())) {
                        queryWrapper.eq(CourseInfoAll::getWeekdays, vo.getWeekdays());
                    }
                if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                        queryWrapper.eq(CourseInfoAll::getRoomNumber, vo.getRoomNumber());
                    }
                if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
                        queryWrapper.eq(CourseInfoAll::getQrCodePath, vo.getQrCodePath());
                    }
                    if(vo.getStartDate() != null){
                        queryWrapper.eq(CourseInfoAll::getStartDate, vo.getStartDate());
                    }
                if(StringUtils.isNotEmpty(vo.getContent())) {
                        queryWrapper.eq(CourseInfoAll::getContent, vo.getContent());
                    }
                    if(vo.getEndDate() != null){
                        queryWrapper.eq(CourseInfoAll::getEndDate, vo.getEndDate());
                    }
                if(StringUtils.isNotEmpty(vo.getMajor())) {
                        queryWrapper.eq(CourseInfoAll::getMajor, vo.getMajor());
                    }
                    if(vo.getBuildingEnumBuildingEnumId1() != null){
                        queryWrapper.eq(CourseInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                    }
                    if(vo.getClassroomInfoClassroomInfoId1() != null){
                        queryWrapper.eq(CourseInfoAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
                    }
                if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
                        queryWrapper.eq(CourseInfoAll::getTimePeriod, vo.getTimePeriod());
                    }
            List<CourseInfoAll> list = courseInfoAllService.list(queryWrapper);
            ExcelUtil<CourseInfoAll> util = new ExcelUtil<>(CourseInfoAll.class);
            util.exportExcel(response, list, "数据");
        }


}

package com.kuafu.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import java.io.Serializable;
import com.kuafu.common.login.IControllerService;

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
import org.apache.commons.lang3.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.kuafu.login.annotation.*;
import com.kuafu.common.domin.PageRequest;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.web.entity.CourseInfoAll;
import com.kuafu.web.service.ICourseInfoAllService;
import com.kuafu.web.vo.CourseInfoAllPageVO;
import com.kuafu.web.entity.SelectVo;
import com.kuafu.web.service.IClassroomInfoService;
import com.kuafu.web.entity.ClassroomInfo;
import com.kuafu.web.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuafu.common.util.QueryUtils;
import org.springframework.context.annotation.Lazy;





/**
 * <p> 课程信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("CourseInfoAllControllerService")
public class CourseInfoAllControllerService implements IControllerService<CourseInfoAll> {


@Autowired
    private  ICourseInfoAllService courseInfoAllService;








    public BaseResponse page( CourseInfoAllPageVO pageVO){
        IPage<CourseInfoAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

        final QueryWrapper<CourseInfoAll> objectQueryWrapper = new QueryWrapper<>();
        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        objectQueryWrapper.orderByDesc("ci.course_info_id");
        LambdaQueryWrapper<CourseInfoAll> queryWrapper = objectQueryWrapper.lambda();


        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
                queryWrapper.like(CourseInfoAll::getCollege, pageVO.getCollege());
            }
            if(pageVO.getEndDate() != null){
                queryWrapper.eq(CourseInfoAll::getEndDate, pageVO.getEndDate());
            }
        if(StringUtils.isNotEmpty(pageVO.getCourseName())) {

                        String s_string = pageVO.getCourseName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(CourseInfoAll::getCourseName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
                queryWrapper.eq(CourseInfoAll::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getWeekdays())) {
                queryWrapper.like(CourseInfoAll::getWeekdays, pageVO.getWeekdays());
            }
        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
                queryWrapper.like(CourseInfoAll::getRoomNumber, pageVO.getRoomNumber());
            }
        if(StringUtils.isNotEmpty(pageVO.getQrCodePath())) {
                queryWrapper.like(CourseInfoAll::getQrCodePath, pageVO.getQrCodePath());
            }
        if(StringUtils.isNotEmpty(pageVO.getContent())) {
                queryWrapper.like(CourseInfoAll::getContent, pageVO.getContent());
            }
            if(pageVO.getBuildingEnumBuildingEnumId1() != null){
                queryWrapper.eq(CourseInfoAll::getBuildingEnumBuildingEnumId1, pageVO.getBuildingEnumBuildingEnumId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
                queryWrapper.like(CourseInfoAll::getMajor, pageVO.getMajor());
            }
            if(pageVO.getCourseInfoId() != null){
                queryWrapper.eq(CourseInfoAll::getCourseInfoId, pageVO.getCourseInfoId());
            }
        if(StringUtils.isNotEmpty(pageVO.getTimePeriod())) {
                queryWrapper.like(CourseInfoAll::getTimePeriod, pageVO.getTimePeriod());
            }
            if(pageVO.getStartDate() != null){
                queryWrapper.eq(CourseInfoAll::getStartDate, pageVO.getStartDate());
            }

                    courseInfoAllService.pageNew(page, pageVO, queryWrapper);
                    page.getRecords().forEach(item -> {
                    });

        page.getRecords().forEach(item -> {
        });


            return ResultUtils.success(page);
        }

        public BaseResponse list(CourseInfoAllPageVO vo){
            LambdaQueryWrapper<CourseInfoAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCollege())) {
                    queryWrapper.eq(CourseInfoAll::getCollege, vo.getCollege());
                }
                if(vo.getEndDate() != null){
                    queryWrapper.eq(CourseInfoAll::getEndDate, vo.getEndDate());
                }
            if(StringUtils.isNotEmpty(vo.getCourseName())) {
                            queryWrapper.like(CourseInfoAll::getCourseName, vo.getCourseName());
                }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
                    queryWrapper.eq(CourseInfoAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
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
            if(StringUtils.isNotEmpty(vo.getContent())) {
                    queryWrapper.eq(CourseInfoAll::getContent, vo.getContent());
                }
                if(vo.getBuildingEnumBuildingEnumId1() != null){
                    queryWrapper.eq(CourseInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
                    queryWrapper.eq(CourseInfoAll::getMajor, vo.getMajor());
                }
            if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
                    queryWrapper.eq(CourseInfoAll::getTimePeriod, vo.getTimePeriod());
                }
                if(vo.getStartDate() != null){
                    queryWrapper.ge(CourseInfoAll::getStartDate, vo.getStartDate());
                }
                return ResultUtils.success(courseInfoAllService.selectListNew(new PageRequest(),queryWrapper,false));
            }


            public BaseResponse get(CourseInfoAllPageVO vo) {


                LambdaQueryWrapper<CourseInfoAll> queryWrapper = new LambdaQueryWrapper<>();


                            if(StringUtils.isNotEmpty(vo.getCollege())) {
                                    queryWrapper.eq(CourseInfoAll::getCollege, vo.getCollege());
                                }


                                if(vo.getEndDate() != null){
                                    queryWrapper.eq(CourseInfoAll::getEndDate, vo.getEndDate());
                                }


                            if(StringUtils.isNotEmpty(vo.getCourseName())) {
                                            queryWrapper.like(CourseInfoAll::getCourseName, vo.getCourseName());
                                }


                                if(vo.getClassroomInfoClassroomInfoId1() != null){
                                    queryWrapper.eq(CourseInfoAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
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


                            if(StringUtils.isNotEmpty(vo.getContent())) {
                                    queryWrapper.eq(CourseInfoAll::getContent, vo.getContent());
                                }


                                if(vo.getBuildingEnumBuildingEnumId1() != null){
                                    queryWrapper.eq(CourseInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getMajor())) {
                                    queryWrapper.eq(CourseInfoAll::getMajor, vo.getMajor());
                                }


                                if(vo.getCourseInfoId() != null){
                                    queryWrapper.eq(CourseInfoAll::getCourseInfoId, vo.getCourseInfoId());
                                }


                            if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
                                    queryWrapper.eq(CourseInfoAll::getTimePeriod, vo.getTimePeriod());
                                }


                                if(vo.getStartDate() != null){
                                    queryWrapper.eq(CourseInfoAll::getStartDate, vo.getStartDate());
                                }

                // CourseInfoAll entity = this.courseInfoAllService.getById(courseInfoId);
                    final Page<CourseInfoAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    courseInfoAllService.pageNew(page, vo, queryWrapper);
                    final List<CourseInfoAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? ResultUtils.success(records.get(0)) : ResultUtils.error(ErrorCode.OPERATION_ERROR);

                    // return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

            public CourseInfoAll getById(Serializable courseInfoId) {

                    LambdaQueryWrapper<CourseInfoAll> queryWrapper = new LambdaQueryWrapper<>();

                            queryWrapper.eq(CourseInfoAll::getCourseInfoId, courseInfoId);

                    final Page<CourseInfoAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    courseInfoAllService.pageNew(page, new PageRequest() , queryWrapper);
                    final List<CourseInfoAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? records.get(0) : null;

            }


        }

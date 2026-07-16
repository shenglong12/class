package com.kuafu.web.controller;

import java.util.List;
import com.kuafu.common.entity.StaticResource;
import com.kuafu.common.resource.service.IStaticResourceService;
import com.kuafu.common.event.EventVo;
import com.kuafu.common.event.MyEventService;
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
import org.springframework.transaction.support.TransactionTemplate;
import com.kuafu.common.sensitive.SensitiveFilter;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.web.entity.CourseInfo;
import com.kuafu.web.service.ICourseInfoService;
import com.kuafu.web.vo.CourseInfoPageVO;
import com.kuafu.web.vo.CourseInfoVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 课程信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("CourseInfoControllerService")
@RequiredArgsConstructor
public class CourseInfoControllerService  {

    private final ICourseInfoService courseInfoService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( CourseInfoPageVO pageVO){
        IPage<CourseInfo> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<CourseInfo> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<CourseInfo> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(CourseInfo::getCourseInfoId);

            if(pageVO.getCourseInfoId() != null){
                queryWrapper.eq(CourseInfo::getCourseInfoId, pageVO.getCourseInfoId());
            }


        if(StringUtils.isNotEmpty(pageVO.getCourseName())) {
//                        queryWrapper.like(CourseInfo::getCourseName, pageVO.getCourseName());
                        String s_string = pageVO.getCourseName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(CourseInfo::getCourseName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }


        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
                queryWrapper.eq(CourseInfo::getCollege, pageVO.getCollege());
            }


        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
                queryWrapper.eq(CourseInfo::getMajor, pageVO.getMajor());
            }


            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
                queryWrapper.eq(CourseInfo::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
            }


            if(pageVO.getStartDate() != null){
                queryWrapper.eq(CourseInfo::getStartDate, pageVO.getStartDate());
            }


            if(pageVO.getEndDate() != null){
                queryWrapper.ge(CourseInfo::getEndDate, pageVO.getEndDate());
            }


        if(StringUtils.isNotEmpty(pageVO.getWeekdays())) {
                queryWrapper.eq(CourseInfo::getWeekdays, pageVO.getWeekdays());
            }


        if(StringUtils.isNotEmpty(pageVO.getTimePeriod())) {
                queryWrapper.eq(CourseInfo::getTimePeriod, pageVO.getTimePeriod());
            }


        if(StringUtils.isNotEmpty(pageVO.getContent())) {
                queryWrapper.eq(CourseInfo::getContent, pageVO.getContent());
            }

            return ResultUtils.success(courseInfoService.page(page, queryWrapper));
        }


        public BaseResponse list(  CourseInfoVO vo){
            LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCourseName())) {
                            queryWrapper.like(CourseInfo::getCourseName, vo.getCourseName());
                }
            if(StringUtils.isNotEmpty(vo.getCollege())) {
                    queryWrapper.eq(CourseInfo::getCollege, vo.getCollege());
                }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
                    queryWrapper.eq(CourseInfo::getMajor, vo.getMajor());
                }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
                    queryWrapper.eq(CourseInfo::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
                }
                if(vo.getStartDate() != null){
                    queryWrapper.ge(CourseInfo::getStartDate, vo.getStartDate());
                }
                if(vo.getEndDate() != null){
                    queryWrapper.ge(CourseInfo::getEndDate, vo.getEndDate());
                }
            if(StringUtils.isNotEmpty(vo.getWeekdays())) {
                    queryWrapper.eq(CourseInfo::getWeekdays, vo.getWeekdays());
                }
            if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
                    queryWrapper.eq(CourseInfo::getTimePeriod, vo.getTimePeriod());
                }
            if(StringUtils.isNotEmpty(vo.getContent())) {
                    queryWrapper.eq(CourseInfo::getContent, vo.getContent());
                }
                return ResultUtils.success(courseInfoService.list(queryWrapper));
            }


            @SensitiveFilter
            public BaseResponse add(  CourseInfoVO vo) {
                CourseInfo entity =  CourseInfo.builder()
                    .courseName(vo.getCourseName())
                    .college(vo.getCollege())
                    .major(vo.getMajor())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .startDate(vo.getStartDate())
                    .endDate(vo.getEndDate())
                    .weekdays(vo.getWeekdays())
                    .timePeriod(vo.getTimePeriod())
                    .content(vo.getContent())
                .build();








                boolean flag =this.courseInfoService.save(entity);






                if(flag
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("course_info").data(entity).build());
                }

                return
flag

                ? ResultUtils.success(entity.getCourseInfoId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( CourseInfoVO vo) {
                CourseInfo entity =  CourseInfo.builder()
                    .courseInfoId(vo.getCourseInfoId())
                    .courseName(vo.getCourseName())
                    .college(vo.getCollege())
                    .major(vo.getMajor())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .startDate(vo.getStartDate())
                    .endDate(vo.getEndDate())
                    .weekdays(vo.getWeekdays())
                    .timePeriod(vo.getTimePeriod())
                    .content(vo.getContent())
                .build();



                        boolean flag = this.courseInfoService.saveOrUpdate(entity);








                return  flag
                         ? ResultUtils.success(entity.getCourseInfoId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( CourseInfoVO vo ) {
                CourseInfo entity = this.courseInfoService.getById(vo.getCourseInfoId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( CourseInfoVO vo) {
                boolean flag = this.courseInfoService.removeById(vo.getCourseInfoId());





                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

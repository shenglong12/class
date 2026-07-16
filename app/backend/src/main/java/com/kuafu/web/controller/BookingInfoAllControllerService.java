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
import com.kuafu.web.entity.BookingInfoAll;
import com.kuafu.web.service.IBookingInfoAllService;
import com.kuafu.web.vo.BookingInfoAllPageVO;
import com.kuafu.web.entity.SelectVo;
import com.kuafu.web.service.IUserInfoService;
import com.kuafu.web.entity.UserInfo;
import com.kuafu.web.service.IClassroomInfoService;
import com.kuafu.web.entity.ClassroomInfo;
import com.kuafu.web.service.IBookingStatusEnumService;
import com.kuafu.web.entity.BookingStatusEnum;
import com.kuafu.web.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuafu.common.util.QueryUtils;
import org.springframework.context.annotation.Lazy;





/**
 * <p> 预约信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("BookingInfoAllControllerService")
public class BookingInfoAllControllerService implements IControllerService<BookingInfoAll> {


@Autowired
    private  IBookingInfoAllService bookingInfoAllService;








    public BaseResponse page( BookingInfoAllPageVO pageVO){
        IPage<BookingInfoAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

        final QueryWrapper<BookingInfoAll> objectQueryWrapper = new QueryWrapper<>();
        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        objectQueryWrapper.orderByDesc("bi.booking_info_id");
        LambdaQueryWrapper<BookingInfoAll> queryWrapper = objectQueryWrapper.lambda();


        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
                queryWrapper.like(BookingInfoAll::getCollege, pageVO.getCollege());
            }
            if(pageVO.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(BookingInfoAll::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
            }
            if(pageVO.getCreateTime() != null){
                queryWrapper.eq(BookingInfoAll::getCreateTime, pageVO.getCreateTime());
            }
            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
                queryWrapper.eq(BookingInfoAll::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getPurpose())) {
                queryWrapper.like(BookingInfoAll::getPurpose, pageVO.getPurpose());
            }
            if(pageVO.getBookingDate() != null){
                queryWrapper.eq(BookingInfoAll::getBookingDate, pageVO.getBookingDate());
            }
        if(StringUtils.isNotEmpty(pageVO.getStatusName())) {

                        String s_string = pageVO.getStatusName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(BookingInfoAll::getStatusName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
            if(pageVO.getBookingInfoId() != null){
                queryWrapper.eq(BookingInfoAll::getBookingInfoId, pageVO.getBookingInfoId());
            }
        if(StringUtils.isNotEmpty(pageVO.getEndTime())) {
                queryWrapper.like(BookingInfoAll::getEndTime, pageVO.getEndTime());
            }
        if(StringUtils.isNotEmpty(pageVO.getStudentId())) {
                queryWrapper.like(BookingInfoAll::getStudentId, pageVO.getStudentId());
            }
            if(pageVO.getRoleEnumRoleEnumId1() != null){
                queryWrapper.eq(BookingInfoAll::getRoleEnumRoleEnumId1, pageVO.getRoleEnumRoleEnumId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
                queryWrapper.like(BookingInfoAll::getRoomNumber, pageVO.getRoomNumber());
            }
        if(StringUtils.isNotEmpty(pageVO.getQrCodePath())) {
                queryWrapper.like(BookingInfoAll::getQrCodePath, pageVO.getQrCodePath());
            }
        if(StringUtils.isNotEmpty(pageVO.getStartTime())) {
                queryWrapper.like(BookingInfoAll::getStartTime, pageVO.getStartTime());
            }
        if(StringUtils.isNotEmpty(pageVO.getPassword())) {
                queryWrapper.like(BookingInfoAll::getPassword, pageVO.getPassword());
            }
            if(pageVO.getBuildingEnumBuildingEnumId1() != null){
                queryWrapper.eq(BookingInfoAll::getBuildingEnumBuildingEnumId1, pageVO.getBuildingEnumBuildingEnumId1());
            }
            if(pageVO.getRegistrationDate() != null){
                queryWrapper.eq(BookingInfoAll::getRegistrationDate, pageVO.getRegistrationDate());
            }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
                queryWrapper.like(BookingInfoAll::getMajor, pageVO.getMajor());
            }
        if(StringUtils.isNotEmpty(pageVO.getName())) {

                        String s_string = pageVO.getName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(BookingInfoAll::getName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
            if(pageVO.getBookingStatusEnumBookingStatusEnumId1() != null){
                queryWrapper.eq(BookingInfoAll::getBookingStatusEnumBookingStatusEnumId1, pageVO.getBookingStatusEnumBookingStatusEnumId1());
            }
            if(pageVO.getUserStatusEnumUserStatusEnumId1() != null){
                queryWrapper.eq(BookingInfoAll::getUserStatusEnumUserStatusEnumId1, pageVO.getUserStatusEnumUserStatusEnumId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
                queryWrapper.like(BookingInfoAll::getPhoneNumber, pageVO.getPhoneNumber());
            }
        if(StringUtils.isNotEmpty(pageVO.getTimePeriod())) {
                queryWrapper.like(BookingInfoAll::getTimePeriod, pageVO.getTimePeriod());
            }

                    bookingInfoAllService.pageNew(page, pageVO, queryWrapper);
                    page.getRecords().forEach(item -> {
                    });

        page.getRecords().forEach(item -> {
        });


            return ResultUtils.success(page);
        }

        public BaseResponse list(BookingInfoAllPageVO vo){
            LambdaQueryWrapper<BookingInfoAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCollege())) {
                    queryWrapper.eq(BookingInfoAll::getCollege, vo.getCollege());
                }
                if(vo.getUserInfoUserInfoId1() != null){
                    queryWrapper.eq(BookingInfoAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                }
                if(vo.getCreateTime() != null){
                    queryWrapper.eq(BookingInfoAll::getCreateTime, vo.getCreateTime());
                }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
                    queryWrapper.eq(BookingInfoAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
                }
            if(StringUtils.isNotEmpty(vo.getPurpose())) {
                    queryWrapper.eq(BookingInfoAll::getPurpose, vo.getPurpose());
                }
                if(vo.getBookingDate() != null){
                    queryWrapper.eq(BookingInfoAll::getBookingDate, vo.getBookingDate());
                }
            if(StringUtils.isNotEmpty(vo.getStatusName())) {
                            queryWrapper.like(BookingInfoAll::getStatusName, vo.getStatusName());
                }
            if(StringUtils.isNotEmpty(vo.getEndTime())) {
                    queryWrapper.eq(BookingInfoAll::getEndTime, vo.getEndTime());
                }
            if(StringUtils.isNotEmpty(vo.getStudentId())) {
                    queryWrapper.eq(BookingInfoAll::getStudentId, vo.getStudentId());
                }
                if(vo.getRoleEnumRoleEnumId1() != null){
                    queryWrapper.eq(BookingInfoAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                }
            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                    queryWrapper.eq(BookingInfoAll::getRoomNumber, vo.getRoomNumber());
                }
            if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
                    queryWrapper.eq(BookingInfoAll::getQrCodePath, vo.getQrCodePath());
                }
            if(StringUtils.isNotEmpty(vo.getStartTime())) {
                    queryWrapper.eq(BookingInfoAll::getStartTime, vo.getStartTime());
                }
            if(StringUtils.isNotEmpty(vo.getPassword())) {
                    queryWrapper.eq(BookingInfoAll::getPassword, vo.getPassword());
                }
                if(vo.getBuildingEnumBuildingEnumId1() != null){
                    queryWrapper.eq(BookingInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                }
                if(vo.getRegistrationDate() != null){
                    queryWrapper.eq(BookingInfoAll::getRegistrationDate, vo.getRegistrationDate());
                }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
                    queryWrapper.eq(BookingInfoAll::getMajor, vo.getMajor());
                }
            if(StringUtils.isNotEmpty(vo.getName())) {
                            queryWrapper.like(BookingInfoAll::getName, vo.getName());
                }
                if(vo.getBookingStatusEnumBookingStatusEnumId1() != null){
                    queryWrapper.eq(BookingInfoAll::getBookingStatusEnumBookingStatusEnumId1, vo.getBookingStatusEnumBookingStatusEnumId1());
                }
                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                    queryWrapper.eq(BookingInfoAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                }
            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                    queryWrapper.eq(BookingInfoAll::getPhoneNumber, vo.getPhoneNumber());
                }
            if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
                    queryWrapper.eq(BookingInfoAll::getTimePeriod, vo.getTimePeriod());
                }
                return ResultUtils.success(bookingInfoAllService.selectListNew(new PageRequest(),queryWrapper,false));
            }


            public BaseResponse get(BookingInfoAllPageVO vo) {


                LambdaQueryWrapper<BookingInfoAll> queryWrapper = new LambdaQueryWrapper<>();


                            if(StringUtils.isNotEmpty(vo.getCollege())) {
                                    queryWrapper.eq(BookingInfoAll::getCollege, vo.getCollege());
                                }


                                if(vo.getUserInfoUserInfoId1() != null){
                                    queryWrapper.eq(BookingInfoAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                                }


                                if(vo.getCreateTime() != null){
                                    queryWrapper.eq(BookingInfoAll::getCreateTime, vo.getCreateTime());
                                }


                                if(vo.getClassroomInfoClassroomInfoId1() != null){
                                    queryWrapper.eq(BookingInfoAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getPurpose())) {
                                    queryWrapper.eq(BookingInfoAll::getPurpose, vo.getPurpose());
                                }


                                if(vo.getBookingDate() != null){
                                    queryWrapper.eq(BookingInfoAll::getBookingDate, vo.getBookingDate());
                                }


                            if(StringUtils.isNotEmpty(vo.getStatusName())) {
                                            queryWrapper.like(BookingInfoAll::getStatusName, vo.getStatusName());
                                }


                                if(vo.getBookingInfoId() != null){
                                    queryWrapper.eq(BookingInfoAll::getBookingInfoId, vo.getBookingInfoId());
                                }


                            if(StringUtils.isNotEmpty(vo.getEndTime())) {
                                    queryWrapper.eq(BookingInfoAll::getEndTime, vo.getEndTime());
                                }


                            if(StringUtils.isNotEmpty(vo.getStudentId())) {
                                    queryWrapper.eq(BookingInfoAll::getStudentId, vo.getStudentId());
                                }


                                if(vo.getRoleEnumRoleEnumId1() != null){
                                    queryWrapper.eq(BookingInfoAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                                    queryWrapper.eq(BookingInfoAll::getRoomNumber, vo.getRoomNumber());
                                }


                            if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
                                    queryWrapper.eq(BookingInfoAll::getQrCodePath, vo.getQrCodePath());
                                }


                            if(StringUtils.isNotEmpty(vo.getStartTime())) {
                                    queryWrapper.eq(BookingInfoAll::getStartTime, vo.getStartTime());
                                }


                            if(StringUtils.isNotEmpty(vo.getPassword())) {
                                    queryWrapper.eq(BookingInfoAll::getPassword, vo.getPassword());
                                }


                                if(vo.getBuildingEnumBuildingEnumId1() != null){
                                    queryWrapper.eq(BookingInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                                }


                                if(vo.getRegistrationDate() != null){
                                    queryWrapper.eq(BookingInfoAll::getRegistrationDate, vo.getRegistrationDate());
                                }


                            if(StringUtils.isNotEmpty(vo.getMajor())) {
                                    queryWrapper.eq(BookingInfoAll::getMajor, vo.getMajor());
                                }


                            if(StringUtils.isNotEmpty(vo.getName())) {
                                            queryWrapper.like(BookingInfoAll::getName, vo.getName());
                                }


                                if(vo.getBookingStatusEnumBookingStatusEnumId1() != null){
                                    queryWrapper.eq(BookingInfoAll::getBookingStatusEnumBookingStatusEnumId1, vo.getBookingStatusEnumBookingStatusEnumId1());
                                }


                                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                                    queryWrapper.eq(BookingInfoAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                                    queryWrapper.eq(BookingInfoAll::getPhoneNumber, vo.getPhoneNumber());
                                }


                            if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
                                    queryWrapper.eq(BookingInfoAll::getTimePeriod, vo.getTimePeriod());
                                }

                // BookingInfoAll entity = this.bookingInfoAllService.getById(bookingInfoId);
                    final Page<BookingInfoAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    bookingInfoAllService.pageNew(page, vo, queryWrapper);
                    final List<BookingInfoAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? ResultUtils.success(records.get(0)) : ResultUtils.error(ErrorCode.OPERATION_ERROR);

                    // return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

            public BookingInfoAll getById(Serializable bookingInfoId) {

                    LambdaQueryWrapper<BookingInfoAll> queryWrapper = new LambdaQueryWrapper<>();

                            queryWrapper.eq(BookingInfoAll::getBookingInfoId, bookingInfoId);

                    final Page<BookingInfoAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    bookingInfoAllService.pageNew(page, new PageRequest() , queryWrapper);
                    final List<BookingInfoAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? records.get(0) : null;

            }


        }

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
import com.kuafu.web.entity.BookingInfoAll;
import com.kuafu.web.service.IBookingInfoAllService;
import com.kuafu.web.vo.BookingInfoAllPageVO;

import com.kuafu.web.service.IUserInfoService;
import com.kuafu.web.entity.UserInfo;
import com.kuafu.web.service.IClassroomInfoService;
import com.kuafu.web.entity.ClassroomInfo;
import com.kuafu.web.service.IBookingStatusEnumService;
import com.kuafu.web.entity.BookingStatusEnum;


/**
 * <p> 预约信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookingInfoAll")
@Api(tags = {"预约信息"})
public class BookingInfoAllController  {

    private final IBookingInfoAllService bookingInfoAllService;

    @PostMapping("page")
    @ApiOperation("全字段分页")
    public BaseResponse page(@RequestBody BookingInfoAllPageVO pageVO){
        IPage<BookingInfoAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<BookingInfoAll> queryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
            queryWrapper.eq(BookingInfoAll::getCollege, pageVO.getCollege());
        }
            if(pageVO.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(BookingInfoAll::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
        }
            if(pageVO.getCreateTime() != null){
            queryWrapper.eq(BookingInfoAll::getCreateTime, pageVO.getCreateTime());
        }
        if(StringUtils.isNotEmpty(pageVO.getPurpose())) {
            queryWrapper.eq(BookingInfoAll::getPurpose, pageVO.getPurpose());
        }
            if(pageVO.getRoleEnumRoleEnumId1() != null){
            queryWrapper.eq(BookingInfoAll::getRoleEnumRoleEnumId1, pageVO.getRoleEnumRoleEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
            queryWrapper.eq(BookingInfoAll::getRoomNumber, pageVO.getRoomNumber());
        }
        if(StringUtils.isNotEmpty(pageVO.getQrCodePath())) {
            queryWrapper.eq(BookingInfoAll::getQrCodePath, pageVO.getQrCodePath());
        }
        if(StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
            queryWrapper.eq(BookingInfoAll::getPhoneNumber, pageVO.getPhoneNumber());
        }
        if(StringUtils.isNotEmpty(pageVO.getEndTime())) {
            queryWrapper.eq(BookingInfoAll::getEndTime, pageVO.getEndTime());
        }
        if(StringUtils.isNotEmpty(pageVO.getStatusName())) {
            queryWrapper.like(BookingInfoAll::getStatusName, pageVO.getStatusName());
        }
            if(pageVO.getRegistrationDate() != null){
            queryWrapper.eq(BookingInfoAll::getRegistrationDate, pageVO.getRegistrationDate());
        }
            if(pageVO.getBookingDate() != null){
            queryWrapper.eq(BookingInfoAll::getBookingDate, pageVO.getBookingDate());
        }
        if(StringUtils.isNotEmpty(pageVO.getStartTime())) {
            queryWrapper.eq(BookingInfoAll::getStartTime, pageVO.getStartTime());
        }
            if(pageVO.getUserStatusEnumUserStatusEnumId1() != null){
            queryWrapper.eq(BookingInfoAll::getUserStatusEnumUserStatusEnumId1, pageVO.getUserStatusEnumUserStatusEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getStudentId())) {
            queryWrapper.eq(BookingInfoAll::getStudentId, pageVO.getStudentId());
        }
        if(StringUtils.isNotEmpty(pageVO.getPassword())) {
            queryWrapper.eq(BookingInfoAll::getPassword, pageVO.getPassword());
        }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
            queryWrapper.eq(BookingInfoAll::getMajor, pageVO.getMajor());
        }
            if(pageVO.getBuildingEnumBuildingEnumId1() != null){
            queryWrapper.eq(BookingInfoAll::getBuildingEnumBuildingEnumId1, pageVO.getBuildingEnumBuildingEnumId1());
        }
            if(pageVO.getBookingStatusEnumBookingStatusEnumId1() != null){
            queryWrapper.eq(BookingInfoAll::getBookingStatusEnumBookingStatusEnumId1, pageVO.getBookingStatusEnumBookingStatusEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getName())) {
            queryWrapper.like(BookingInfoAll::getName, pageVO.getName());
        }
            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(BookingInfoAll::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getTimePeriod())) {
            queryWrapper.eq(BookingInfoAll::getTimePeriod, pageVO.getTimePeriod());
        }
        return ResultUtils.success(bookingInfoAllService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("全字段列表")
    public BaseResponse list(@RequestBody BookingInfoAllPageVO vo){
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
            if(StringUtils.isNotEmpty(vo.getPurpose())) {
            queryWrapper.eq(BookingInfoAll::getPurpose, vo.getPurpose());
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
            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
            queryWrapper.eq(BookingInfoAll::getPhoneNumber, vo.getPhoneNumber());
        }
            if(StringUtils.isNotEmpty(vo.getEndTime())) {
            queryWrapper.eq(BookingInfoAll::getEndTime, vo.getEndTime());
        }
            if(StringUtils.isNotEmpty(vo.getStatusName())) {
            queryWrapper.like(BookingInfoAll::getStatusName, vo.getStatusName());
        }
                if(vo.getRegistrationDate() != null){
            queryWrapper.eq(BookingInfoAll::getRegistrationDate, vo.getRegistrationDate());
        }
                if(vo.getBookingDate() != null){
            queryWrapper.eq(BookingInfoAll::getBookingDate, vo.getBookingDate());
        }
            if(StringUtils.isNotEmpty(vo.getStartTime())) {
            queryWrapper.eq(BookingInfoAll::getStartTime, vo.getStartTime());
        }
                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
            queryWrapper.eq(BookingInfoAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getStudentId())) {
            queryWrapper.eq(BookingInfoAll::getStudentId, vo.getStudentId());
        }
            if(StringUtils.isNotEmpty(vo.getPassword())) {
            queryWrapper.eq(BookingInfoAll::getPassword, vo.getPassword());
        }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
            queryWrapper.eq(BookingInfoAll::getMajor, vo.getMajor());
        }
                if(vo.getBuildingEnumBuildingEnumId1() != null){
            queryWrapper.eq(BookingInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
        }
                if(vo.getBookingStatusEnumBookingStatusEnumId1() != null){
            queryWrapper.eq(BookingInfoAll::getBookingStatusEnumBookingStatusEnumId1, vo.getBookingStatusEnumBookingStatusEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getName())) {
            queryWrapper.like(BookingInfoAll::getName, vo.getName());
        }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(BookingInfoAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
        }
            if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
            queryWrapper.eq(BookingInfoAll::getTimePeriod, vo.getTimePeriod());
        }
        return ResultUtils.success(bookingInfoAllService.list(queryWrapper));
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer bookingInfoId) {
        BookingInfoAll entity = this.bookingInfoAllService.getById(bookingInfoId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


        private final IUserInfoService userInfoService;

        @GetMapping("get/user_info_list")
        @ApiOperation("查询下拉框的外键信息")
        public BaseResponse get_user_info_list() {
            List<UserInfo> list = this.userInfoService.list();
            final List<SelectVO> selectVoList = list.stream().map(p ->
                new SelectVO(p.getUserInfoId(), p.getName().toString()))
                        .collect(Collectors.toList());
            return ResultUtils.success(selectVoList);
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
        private final IBookingStatusEnumService bookingStatusEnumService;

        @GetMapping("get/booking_status_enum_list")
        @ApiOperation("查询下拉框的外键信息")
        public BaseResponse get_booking_status_enum_list() {
            List<BookingStatusEnum> list = this.bookingStatusEnumService.list();
            final List<SelectVO> selectVoList = list.stream().map(p ->
                new SelectVO(p.getBookingStatusEnumId(), p.getStatusName().toString()))
                        .collect(Collectors.toList());
            return ResultUtils.success(selectVoList);
        }


        /**
         * 导出excel
         */
        @PostMapping("/export")
        public void export(HttpServletResponse response, @RequestBody BookingInfoAllPageVO vo) {

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
                if(StringUtils.isNotEmpty(vo.getPurpose())) {
                        queryWrapper.eq(BookingInfoAll::getPurpose, vo.getPurpose());
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
                if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                        queryWrapper.eq(BookingInfoAll::getPhoneNumber, vo.getPhoneNumber());
                    }
                if(StringUtils.isNotEmpty(vo.getEndTime())) {
                        queryWrapper.eq(BookingInfoAll::getEndTime, vo.getEndTime());
                    }
                if(StringUtils.isNotEmpty(vo.getStatusName())) {
                                queryWrapper.like(BookingInfoAll::getStatusName, vo.getStatusName());
                    }
                    if(vo.getRegistrationDate() != null){
                        queryWrapper.eq(BookingInfoAll::getRegistrationDate, vo.getRegistrationDate());
                    }
                    if(vo.getBookingDate() != null){
                        queryWrapper.eq(BookingInfoAll::getBookingDate, vo.getBookingDate());
                    }
                if(StringUtils.isNotEmpty(vo.getStartTime())) {
                        queryWrapper.eq(BookingInfoAll::getStartTime, vo.getStartTime());
                    }
                    if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                        queryWrapper.eq(BookingInfoAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                    }
                if(StringUtils.isNotEmpty(vo.getStudentId())) {
                        queryWrapper.eq(BookingInfoAll::getStudentId, vo.getStudentId());
                    }
                if(StringUtils.isNotEmpty(vo.getPassword())) {
                        queryWrapper.eq(BookingInfoAll::getPassword, vo.getPassword());
                    }
                if(StringUtils.isNotEmpty(vo.getMajor())) {
                        queryWrapper.eq(BookingInfoAll::getMajor, vo.getMajor());
                    }
                    if(vo.getBuildingEnumBuildingEnumId1() != null){
                        queryWrapper.eq(BookingInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                    }
                    if(vo.getBookingStatusEnumBookingStatusEnumId1() != null){
                        queryWrapper.eq(BookingInfoAll::getBookingStatusEnumBookingStatusEnumId1, vo.getBookingStatusEnumBookingStatusEnumId1());
                    }
                if(StringUtils.isNotEmpty(vo.getName())) {
                                queryWrapper.like(BookingInfoAll::getName, vo.getName());
                    }
                    if(vo.getClassroomInfoClassroomInfoId1() != null){
                        queryWrapper.eq(BookingInfoAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
                    }
                if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
                        queryWrapper.eq(BookingInfoAll::getTimePeriod, vo.getTimePeriod());
                    }
            List<BookingInfoAll> list = bookingInfoAllService.list(queryWrapper);
            ExcelUtil<BookingInfoAll> util = new ExcelUtil<>(BookingInfoAll.class);
            util.exportExcel(response, list, "数据");
        }


}

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
import com.kuafu.web.entity.BookingInfo;
import com.kuafu.web.service.IBookingInfoService;
import com.kuafu.web.vo.BookingInfoPageVO;
import com.kuafu.web.vo.BookingInfoVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 预约信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("BookingInfoControllerService")
@RequiredArgsConstructor
public class BookingInfoControllerService  {

    private final IBookingInfoService bookingInfoService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( BookingInfoPageVO pageVO){
        IPage<BookingInfo> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<BookingInfo> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<BookingInfo> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(BookingInfo::getBookingInfoId);

            if(pageVO.getBookingInfoId() != null){
                queryWrapper.eq(BookingInfo::getBookingInfoId, pageVO.getBookingInfoId());
            }


            if(pageVO.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(BookingInfo::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
            }


            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
                queryWrapper.eq(BookingInfo::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
            }


            if(pageVO.getBookingDate() != null){
                queryWrapper.eq(BookingInfo::getBookingDate, pageVO.getBookingDate());
            }


        if(StringUtils.isNotEmpty(pageVO.getTimePeriod())) {
                queryWrapper.eq(BookingInfo::getTimePeriod, pageVO.getTimePeriod());
            }


        if(StringUtils.isNotEmpty(pageVO.getPurpose())) {
                queryWrapper.eq(BookingInfo::getPurpose, pageVO.getPurpose());
            }


            if(pageVO.getBookingStatusEnumBookingStatusEnumId1() != null){
                queryWrapper.eq(BookingInfo::getBookingStatusEnumBookingStatusEnumId1, pageVO.getBookingStatusEnumBookingStatusEnumId1());
            }


            if(pageVO.getCreateTime() != null){
                queryWrapper.eq(BookingInfo::getCreateTime, pageVO.getCreateTime());
            }


        if(StringUtils.isNotEmpty(pageVO.getStartTime())) {
                queryWrapper.eq(BookingInfo::getStartTime, pageVO.getStartTime());
            }


        if(StringUtils.isNotEmpty(pageVO.getEndTime())) {
                queryWrapper.eq(BookingInfo::getEndTime, pageVO.getEndTime());
            }

            return ResultUtils.success(bookingInfoService.page(page, queryWrapper));
        }


        public BaseResponse list(  BookingInfoVO vo){
            LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

                if(vo.getUserInfoUserInfoId1() != null){
                    queryWrapper.eq(BookingInfo::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
                    queryWrapper.eq(BookingInfo::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
                }
                if(vo.getBookingDate() != null){
                    queryWrapper.eq(BookingInfo::getBookingDate, vo.getBookingDate());
                }
            if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
                    queryWrapper.eq(BookingInfo::getTimePeriod, vo.getTimePeriod());
                }
            if(StringUtils.isNotEmpty(vo.getPurpose())) {
                    queryWrapper.eq(BookingInfo::getPurpose, vo.getPurpose());
                }
                if(vo.getBookingStatusEnumBookingStatusEnumId1() != null){
                    queryWrapper.eq(BookingInfo::getBookingStatusEnumBookingStatusEnumId1, vo.getBookingStatusEnumBookingStatusEnumId1());
                }
                if(vo.getCreateTime() != null){
                    queryWrapper.eq(BookingInfo::getCreateTime, vo.getCreateTime());
                }
            if(StringUtils.isNotEmpty(vo.getStartTime())) {
                    queryWrapper.eq(BookingInfo::getStartTime, vo.getStartTime());
                }
            if(StringUtils.isNotEmpty(vo.getEndTime())) {
                    queryWrapper.eq(BookingInfo::getEndTime, vo.getEndTime());
                }
                return ResultUtils.success(bookingInfoService.list(queryWrapper));
            }


            @SensitiveFilter
            public BaseResponse add(  BookingInfoVO vo) {
                BookingInfo entity =  BookingInfo.builder()
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .bookingDate(vo.getBookingDate())
                    .timePeriod(vo.getTimePeriod())
                    .purpose(vo.getPurpose())
                    .bookingStatusEnumBookingStatusEnumId1(vo.getBookingStatusEnumBookingStatusEnumId1())
                    .startTime(vo.getStartTime())
                    .endTime(vo.getEndTime())
                .build();








                boolean flag =this.bookingInfoService.save(entity);






                if(flag
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("booking_info").data(entity).build());
                }

                return
flag

                ? ResultUtils.success(entity.getBookingInfoId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( BookingInfoVO vo) {
                BookingInfo entity =  BookingInfo.builder()
                    .bookingInfoId(vo.getBookingInfoId())
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .bookingDate(vo.getBookingDate())
                    .timePeriod(vo.getTimePeriod())
                    .purpose(vo.getPurpose())
                    .bookingStatusEnumBookingStatusEnumId1(vo.getBookingStatusEnumBookingStatusEnumId1())
                    .startTime(vo.getStartTime())
                    .endTime(vo.getEndTime())
                .build();



                        boolean flag = this.bookingInfoService.saveOrUpdate(entity);








                return  flag
                         ? ResultUtils.success(entity.getBookingInfoId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( BookingInfoVO vo ) {
                BookingInfo entity = this.bookingInfoService.getById(vo.getBookingInfoId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( BookingInfoVO vo) {
                boolean flag = this.bookingInfoService.removeById(vo.getBookingInfoId());





                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

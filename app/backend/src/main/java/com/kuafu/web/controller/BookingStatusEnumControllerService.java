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
import com.kuafu.web.entity.BookingStatusEnum;
import com.kuafu.web.service.IBookingStatusEnumService;
import com.kuafu.web.vo.BookingStatusEnumPageVO;
import com.kuafu.web.vo.BookingStatusEnumVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 预约状态 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("BookingStatusEnumControllerService")
@RequiredArgsConstructor
public class BookingStatusEnumControllerService  {

    private final IBookingStatusEnumService bookingStatusEnumService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( BookingStatusEnumPageVO pageVO){
        IPage<BookingStatusEnum> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<BookingStatusEnum> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<BookingStatusEnum> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(BookingStatusEnum::getBookingStatusEnumId);

            if(pageVO.getBookingStatusEnumId() != null){
                queryWrapper.eq(BookingStatusEnum::getBookingStatusEnumId, pageVO.getBookingStatusEnumId());
            }


        if(StringUtils.isNotEmpty(pageVO.getStatusName())) {
//                        queryWrapper.like(BookingStatusEnum::getStatusName, pageVO.getStatusName());
                        String s_string = pageVO.getStatusName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(BookingStatusEnum::getStatusName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }

            return ResultUtils.success(bookingStatusEnumService.page(page, queryWrapper));
        }


        public BaseResponse list(  BookingStatusEnumVO vo){
            LambdaQueryWrapper<BookingStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getStatusName())) {
                            queryWrapper.like(BookingStatusEnum::getStatusName, vo.getStatusName());
                }
                return ResultUtils.success(bookingStatusEnumService.list(queryWrapper));
            }


            @SensitiveFilter
            public BaseResponse add(  BookingStatusEnumVO vo) {
                BookingStatusEnum entity =  BookingStatusEnum.builder()
                    .statusName(vo.getStatusName())
                .build();








                boolean flag =this.bookingStatusEnumService.save(entity);






                if(flag
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("booking_status_enum").data(entity).build());
                }

                return
flag

                ? ResultUtils.success(entity.getBookingStatusEnumId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( BookingStatusEnumVO vo) {
                BookingStatusEnum entity =  BookingStatusEnum.builder()
                    .bookingStatusEnumId(vo.getBookingStatusEnumId())
                    .statusName(vo.getStatusName())
                .build();



                        boolean flag = this.bookingStatusEnumService.saveOrUpdate(entity);








                return  flag
                         ? ResultUtils.success(entity.getBookingStatusEnumId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( BookingStatusEnumVO vo ) {
                BookingStatusEnum entity = this.bookingStatusEnumService.getById(vo.getBookingStatusEnumId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( BookingStatusEnumVO vo) {
                boolean flag = this.bookingStatusEnumService.removeById(vo.getBookingStatusEnumId());





                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

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
import com.kuafu.web.entity.UserStatusEnum;
import com.kuafu.web.service.IUserStatusEnumService;
import com.kuafu.web.vo.UserStatusEnumPageVO;
import com.kuafu.web.vo.UserStatusEnumVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 用户状态 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("UserStatusEnumControllerService")
@RequiredArgsConstructor
public class UserStatusEnumControllerService  {

    private final IUserStatusEnumService userStatusEnumService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( UserStatusEnumPageVO pageVO){
        IPage<UserStatusEnum> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<UserStatusEnum> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<UserStatusEnum> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(UserStatusEnum::getUserStatusEnumId);

            if(pageVO.getUserStatusEnumId() != null){
                queryWrapper.eq(UserStatusEnum::getUserStatusEnumId, pageVO.getUserStatusEnumId());
            }


        if(StringUtils.isNotEmpty(pageVO.getStatusName())) {
//                        queryWrapper.like(UserStatusEnum::getStatusName, pageVO.getStatusName());
                        String s_string = pageVO.getStatusName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(UserStatusEnum::getStatusName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }

            return ResultUtils.success(userStatusEnumService.page(page, queryWrapper));
        }


        public BaseResponse list(  UserStatusEnumVO vo){
            LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getStatusName())) {
                            queryWrapper.like(UserStatusEnum::getStatusName, vo.getStatusName());
                }
                return ResultUtils.success(userStatusEnumService.list(queryWrapper));
            }


            @SensitiveFilter
            public BaseResponse add(  UserStatusEnumVO vo) {
                UserStatusEnum entity =  UserStatusEnum.builder()
                    .statusName(vo.getStatusName())
                .build();








                boolean flag =this.userStatusEnumService.save(entity);






                if(flag
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("user_status_enum").data(entity).build());
                }

                return
flag

                ? ResultUtils.success(entity.getUserStatusEnumId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( UserStatusEnumVO vo) {
                UserStatusEnum entity =  UserStatusEnum.builder()
                    .userStatusEnumId(vo.getUserStatusEnumId())
                    .statusName(vo.getStatusName())
                .build();



                        boolean flag = this.userStatusEnumService.saveOrUpdate(entity);








                return  flag
                         ? ResultUtils.success(entity.getUserStatusEnumId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( UserStatusEnumVO vo ) {
                UserStatusEnum entity = this.userStatusEnumService.getById(vo.getUserStatusEnumId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( UserStatusEnumVO vo) {
                boolean flag = this.userStatusEnumService.removeById(vo.getUserStatusEnumId());





                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

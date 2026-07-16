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
import com.kuafu.web.entity.RoleEnum;
import com.kuafu.web.service.IRoleEnumService;
import com.kuafu.web.vo.RoleEnumPageVO;
import com.kuafu.web.vo.RoleEnumVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 角色枚举 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("RoleEnumControllerService")
@RequiredArgsConstructor
public class RoleEnumControllerService  {

    private final IRoleEnumService roleEnumService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( RoleEnumPageVO pageVO){
        IPage<RoleEnum> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<RoleEnum> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<RoleEnum> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(RoleEnum::getRoleEnumId);

            if(pageVO.getRoleEnumId() != null){
                queryWrapper.eq(RoleEnum::getRoleEnumId, pageVO.getRoleEnumId());
            }


        if(StringUtils.isNotEmpty(pageVO.getRoleName())) {
//                        queryWrapper.like(RoleEnum::getRoleName, pageVO.getRoleName());
                        String s_string = pageVO.getRoleName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(RoleEnum::getRoleName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }

            return ResultUtils.success(roleEnumService.page(page, queryWrapper));
        }


        public BaseResponse list(  RoleEnumVO vo){
            LambdaQueryWrapper<RoleEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getRoleName())) {
                            queryWrapper.like(RoleEnum::getRoleName, vo.getRoleName());
                }
                return ResultUtils.success(roleEnumService.list(queryWrapper));
            }


            @SensitiveFilter
            public BaseResponse add(  RoleEnumVO vo) {
                RoleEnum entity =  RoleEnum.builder()
                    .roleName(vo.getRoleName())
                .build();








                boolean flag =this.roleEnumService.save(entity);






                if(flag
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("role_enum").data(entity).build());
                }

                return
flag

                ? ResultUtils.success(entity.getRoleEnumId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( RoleEnumVO vo) {
                RoleEnum entity =  RoleEnum.builder()
                    .roleEnumId(vo.getRoleEnumId())
                    .roleName(vo.getRoleName())
                .build();



                        boolean flag = this.roleEnumService.saveOrUpdate(entity);








                return  flag
                         ? ResultUtils.success(entity.getRoleEnumId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( RoleEnumVO vo ) {
                RoleEnum entity = this.roleEnumService.getById(vo.getRoleEnumId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( RoleEnumVO vo) {
                boolean flag = this.roleEnumService.removeById(vo.getRoleEnumId());





                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

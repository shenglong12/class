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
import com.kuafu.web.entity.RepairStatusEnum;
import com.kuafu.web.service.IRepairStatusEnumService;
import com.kuafu.web.vo.RepairStatusEnumPageVO;
import com.kuafu.web.vo.RepairStatusEnumVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 报修状态 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("RepairStatusEnumControllerService")
@RequiredArgsConstructor
public class RepairStatusEnumControllerService  {

    private final IRepairStatusEnumService repairStatusEnumService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( RepairStatusEnumPageVO pageVO){
        IPage<RepairStatusEnum> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<RepairStatusEnum> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<RepairStatusEnum> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(RepairStatusEnum::getRepairStatusEnumId);

            if(pageVO.getRepairStatusEnumId() != null){
                queryWrapper.eq(RepairStatusEnum::getRepairStatusEnumId, pageVO.getRepairStatusEnumId());
            }


        if(StringUtils.isNotEmpty(pageVO.getStatusName())) {
//                        queryWrapper.like(RepairStatusEnum::getStatusName, pageVO.getStatusName());
                        String s_string = pageVO.getStatusName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(RepairStatusEnum::getStatusName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }

            return ResultUtils.success(repairStatusEnumService.page(page, queryWrapper));
        }


        public BaseResponse list(  RepairStatusEnumVO vo){
            LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getStatusName())) {
                            queryWrapper.like(RepairStatusEnum::getStatusName, vo.getStatusName());
                }
                return ResultUtils.success(repairStatusEnumService.list(queryWrapper));
            }


            @SensitiveFilter
            public BaseResponse add(  RepairStatusEnumVO vo) {
                RepairStatusEnum entity =  RepairStatusEnum.builder()
                    .statusName(vo.getStatusName())
                .build();








                boolean flag =this.repairStatusEnumService.save(entity);






                if(flag
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("repair_status_enum").data(entity).build());
                }

                return
flag

                ? ResultUtils.success(entity.getRepairStatusEnumId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( RepairStatusEnumVO vo) {
                RepairStatusEnum entity =  RepairStatusEnum.builder()
                    .repairStatusEnumId(vo.getRepairStatusEnumId())
                    .statusName(vo.getStatusName())
                .build();



                        boolean flag = this.repairStatusEnumService.saveOrUpdate(entity);








                return  flag
                         ? ResultUtils.success(entity.getRepairStatusEnumId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( RepairStatusEnumVO vo ) {
                RepairStatusEnum entity = this.repairStatusEnumService.getById(vo.getRepairStatusEnumId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( RepairStatusEnumVO vo) {
                boolean flag = this.repairStatusEnumService.removeById(vo.getRepairStatusEnumId());





                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

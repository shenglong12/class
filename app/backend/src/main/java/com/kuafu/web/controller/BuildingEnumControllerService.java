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
import com.kuafu.web.entity.BuildingEnum;
import com.kuafu.web.service.IBuildingEnumService;
import com.kuafu.web.vo.BuildingEnumPageVO;
import com.kuafu.web.vo.BuildingEnumVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 教学楼枚举 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("BuildingEnumControllerService")
@RequiredArgsConstructor
public class BuildingEnumControllerService  {

    private final IBuildingEnumService buildingEnumService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( BuildingEnumPageVO pageVO){
        IPage<BuildingEnum> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<BuildingEnum> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<BuildingEnum> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(BuildingEnum::getBuildingEnumId);

            if(pageVO.getBuildingEnumId() != null){
                queryWrapper.eq(BuildingEnum::getBuildingEnumId, pageVO.getBuildingEnumId());
            }


        if(StringUtils.isNotEmpty(pageVO.getBuildingName())) {
//                        queryWrapper.like(BuildingEnum::getBuildingName, pageVO.getBuildingName());
                        String s_string = pageVO.getBuildingName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(BuildingEnum::getBuildingName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }

            return ResultUtils.success(buildingEnumService.page(page, queryWrapper));
        }


        public BaseResponse list(  BuildingEnumVO vo){
            LambdaQueryWrapper<BuildingEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getBuildingName())) {
                            queryWrapper.like(BuildingEnum::getBuildingName, vo.getBuildingName());
                }
                return ResultUtils.success(buildingEnumService.list(queryWrapper));
            }


            @SensitiveFilter
            public BaseResponse add(  BuildingEnumVO vo) {
                BuildingEnum entity =  BuildingEnum.builder()
                    .buildingName(vo.getBuildingName())
                .build();








                boolean flag =this.buildingEnumService.save(entity);






                if(flag
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("building_enum").data(entity).build());
                }

                return
flag

                ? ResultUtils.success(entity.getBuildingEnumId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( BuildingEnumVO vo) {
                BuildingEnum entity =  BuildingEnum.builder()
                    .buildingEnumId(vo.getBuildingEnumId())
                    .buildingName(vo.getBuildingName())
                .build();



                        boolean flag = this.buildingEnumService.saveOrUpdate(entity);








                return  flag
                         ? ResultUtils.success(entity.getBuildingEnumId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( BuildingEnumVO vo ) {
                BuildingEnum entity = this.buildingEnumService.getById(vo.getBuildingEnumId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( BuildingEnumVO vo) {
                boolean flag = this.buildingEnumService.removeById(vo.getBuildingEnumId());





                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

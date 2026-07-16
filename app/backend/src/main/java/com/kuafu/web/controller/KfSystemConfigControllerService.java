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
import com.kuafu.web.entity.KfSystemConfig;
import com.kuafu.web.service.IKfSystemConfigService;
import com.kuafu.web.vo.KfSystemConfigPageVO;
import com.kuafu.web.vo.KfSystemConfigVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 系统配置 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("KfSystemConfigControllerService")
@RequiredArgsConstructor
public class KfSystemConfigControllerService  {

    private final IKfSystemConfigService kfSystemConfigService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( KfSystemConfigPageVO pageVO){
        IPage<KfSystemConfig> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<KfSystemConfig> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<KfSystemConfig> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(KfSystemConfig::getId);

            if(pageVO.getId() != null){
                queryWrapper.eq(KfSystemConfig::getId, pageVO.getId());
            }


        if(StringUtils.isNotEmpty(pageVO.getName())) {
//                        queryWrapper.like(KfSystemConfig::getName, pageVO.getName());
                        String s_string = pageVO.getName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(KfSystemConfig::getName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }


        if(StringUtils.isNotEmpty(pageVO.getChineseName())) {
//                        queryWrapper.like(KfSystemConfig::getChineseName, pageVO.getChineseName());
                        String s_string = pageVO.getChineseName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(KfSystemConfig::getChineseName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }


        if(StringUtils.isNotEmpty(pageVO.getDescription())) {
                queryWrapper.eq(KfSystemConfig::getDescription, pageVO.getDescription());
            }


        if(StringUtils.isNotEmpty(pageVO.getContent())) {
                queryWrapper.eq(KfSystemConfig::getContent, pageVO.getContent());
            }


        if(StringUtils.isNotEmpty(pageVO.getRemark())) {
                queryWrapper.eq(KfSystemConfig::getRemark, pageVO.getRemark());
            }


        if(StringUtils.isNotEmpty(pageVO.getType())) {
                queryWrapper.eq(KfSystemConfig::getType, pageVO.getType());
            }

            return ResultUtils.success(kfSystemConfigService.page(page, queryWrapper));
        }


        public BaseResponse list(  KfSystemConfigVO vo){
            LambdaQueryWrapper<KfSystemConfig> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getName())) {
                            queryWrapper.like(KfSystemConfig::getName, vo.getName());
                }
            if(StringUtils.isNotEmpty(vo.getChineseName())) {
                            queryWrapper.like(KfSystemConfig::getChineseName, vo.getChineseName());
                }
            if(StringUtils.isNotEmpty(vo.getDescription())) {
                    queryWrapper.eq(KfSystemConfig::getDescription, vo.getDescription());
                }
            if(StringUtils.isNotEmpty(vo.getContent())) {
                    queryWrapper.eq(KfSystemConfig::getContent, vo.getContent());
                }
            if(StringUtils.isNotEmpty(vo.getRemark())) {
                    queryWrapper.eq(KfSystemConfig::getRemark, vo.getRemark());
                }
            if(StringUtils.isNotEmpty(vo.getType())) {
                    queryWrapper.eq(KfSystemConfig::getType, vo.getType());
                }
                return ResultUtils.success(kfSystemConfigService.list(queryWrapper));
            }


            @SensitiveFilter
            public BaseResponse add(  KfSystemConfigVO vo) {
                KfSystemConfig entity =  KfSystemConfig.builder()
                    .name(vo.getName())
                    .chineseName(vo.getChineseName())
                    .description(vo.getDescription())
                    .content(vo.getContent())
                    .remark(vo.getRemark())
                    .type(vo.getType())
                .build();








                boolean flag =this.kfSystemConfigService.save(entity);






                if(flag
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("kf_system_config").data(entity).build());
                }

                return
flag

                ? ResultUtils.success(entity.getId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( KfSystemConfigVO vo) {
                KfSystemConfig entity =  KfSystemConfig.builder()
                    .id(vo.getId())
                    .name(vo.getName())
                    .chineseName(vo.getChineseName())
                    .description(vo.getDescription())
                    .content(vo.getContent())
                    .remark(vo.getRemark())
                    .type(vo.getType())
                .build();



                        boolean flag = this.kfSystemConfigService.saveOrUpdate(entity);








                return  flag
                         ? ResultUtils.success(entity.getId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( KfSystemConfigVO vo ) {
                KfSystemConfig entity = this.kfSystemConfigService.getById(vo.getId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( KfSystemConfigVO vo) {
                boolean flag = this.kfSystemConfigService.removeById(vo.getId());





                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

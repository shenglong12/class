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
import com.kuafu.web.entity.ClassroomInfoAll;
import com.kuafu.web.service.IClassroomInfoAllService;
import com.kuafu.web.vo.ClassroomInfoAllPageVO;
import com.kuafu.web.entity.SelectVo;
import com.kuafu.web.service.IBuildingEnumService;
import com.kuafu.web.entity.BuildingEnum;
import com.kuafu.web.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuafu.common.util.QueryUtils;
import org.springframework.context.annotation.Lazy;





/**
 * <p> 教室信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("ClassroomInfoAllControllerService")
public class ClassroomInfoAllControllerService implements IControllerService<ClassroomInfoAll> {


@Autowired
    private  IClassroomInfoAllService classroomInfoAllService;








    public BaseResponse page( ClassroomInfoAllPageVO pageVO){
        IPage<ClassroomInfoAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

        final QueryWrapper<ClassroomInfoAll> objectQueryWrapper = new QueryWrapper<>();
        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        objectQueryWrapper.orderByDesc("ci.classroom_info_id");
        LambdaQueryWrapper<ClassroomInfoAll> queryWrapper = objectQueryWrapper.lambda();


        if(StringUtils.isNotEmpty(pageVO.getBuildingName())) {

                        String s_string = pageVO.getBuildingName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(ClassroomInfoAll::getBuildingName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
            if(pageVO.getBuildingEnumBuildingEnumId1() != null){
                queryWrapper.eq(ClassroomInfoAll::getBuildingEnumBuildingEnumId1, pageVO.getBuildingEnumBuildingEnumId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
                queryWrapper.like(ClassroomInfoAll::getRoomNumber, pageVO.getRoomNumber());
            }
        if(StringUtils.isNotEmpty(pageVO.getQrCodePath())) {
                queryWrapper.like(ClassroomInfoAll::getQrCodePath, pageVO.getQrCodePath());
            }
            if(pageVO.getClassroomInfoId() != null){
                queryWrapper.eq(ClassroomInfoAll::getClassroomInfoId, pageVO.getClassroomInfoId());
            }

                    classroomInfoAllService.pageNew(page, pageVO, queryWrapper);
                    page.getRecords().forEach(item -> {
                    });

        page.getRecords().forEach(item -> {
        });


            return ResultUtils.success(page);
        }

        public BaseResponse list(ClassroomInfoAllPageVO vo){
            LambdaQueryWrapper<ClassroomInfoAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getBuildingName())) {
                            queryWrapper.like(ClassroomInfoAll::getBuildingName, vo.getBuildingName());
                }
                if(vo.getBuildingEnumBuildingEnumId1() != null){
                    queryWrapper.eq(ClassroomInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                }
            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                    queryWrapper.eq(ClassroomInfoAll::getRoomNumber, vo.getRoomNumber());
                }
            if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
                    queryWrapper.eq(ClassroomInfoAll::getQrCodePath, vo.getQrCodePath());
                }
                return ResultUtils.success(classroomInfoAllService.selectListNew(new PageRequest(),queryWrapper,false));
            }


            public BaseResponse get(ClassroomInfoAllPageVO vo) {


                LambdaQueryWrapper<ClassroomInfoAll> queryWrapper = new LambdaQueryWrapper<>();


                            if(StringUtils.isNotEmpty(vo.getBuildingName())) {
                                            queryWrapper.like(ClassroomInfoAll::getBuildingName, vo.getBuildingName());
                                }


                                if(vo.getBuildingEnumBuildingEnumId1() != null){
                                    queryWrapper.eq(ClassroomInfoAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                                    queryWrapper.eq(ClassroomInfoAll::getRoomNumber, vo.getRoomNumber());
                                }


                            if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
                                    queryWrapper.eq(ClassroomInfoAll::getQrCodePath, vo.getQrCodePath());
                                }


                                if(vo.getClassroomInfoId() != null){
                                    queryWrapper.eq(ClassroomInfoAll::getClassroomInfoId, vo.getClassroomInfoId());
                                }

                // ClassroomInfoAll entity = this.classroomInfoAllService.getById(classroomInfoId);
                    final Page<ClassroomInfoAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    classroomInfoAllService.pageNew(page, vo, queryWrapper);
                    final List<ClassroomInfoAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? ResultUtils.success(records.get(0)) : ResultUtils.error(ErrorCode.OPERATION_ERROR);

                    // return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

            public ClassroomInfoAll getById(Serializable classroomInfoId) {

                    LambdaQueryWrapper<ClassroomInfoAll> queryWrapper = new LambdaQueryWrapper<>();

                            queryWrapper.eq(ClassroomInfoAll::getClassroomInfoId, classroomInfoId);

                    final Page<ClassroomInfoAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    classroomInfoAllService.pageNew(page, new PageRequest() , queryWrapper);
                    final List<ClassroomInfoAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? records.get(0) : null;

            }


        }

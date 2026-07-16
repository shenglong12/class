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
import com.kuafu.web.entity.BackgroundImageAll;
import com.kuafu.web.service.IBackgroundImageAllService;
import com.kuafu.web.vo.BackgroundImageAllPageVO;
import com.kuafu.web.entity.SelectVo;
import com.kuafu.web.service.IUserInfoService;
import com.kuafu.web.entity.UserInfo;
import com.kuafu.web.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuafu.common.util.QueryUtils;
import org.springframework.context.annotation.Lazy;





/**
 * <p> 背景图片 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("BackgroundImageAllControllerService")
public class BackgroundImageAllControllerService implements IControllerService<BackgroundImageAll> {


@Autowired
    private  IBackgroundImageAllService backgroundImageAllService;








    public BaseResponse page( BackgroundImageAllPageVO pageVO){
        IPage<BackgroundImageAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

        final QueryWrapper<BackgroundImageAll> objectQueryWrapper = new QueryWrapper<>();
        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        objectQueryWrapper.orderByDesc("bi.background_image_id");
        LambdaQueryWrapper<BackgroundImageAll> queryWrapper = objectQueryWrapper.lambda();


        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
                queryWrapper.like(BackgroundImageAll::getCollege, pageVO.getCollege());
            }
            if(pageVO.getUploadTime() != null){
                queryWrapper.eq(BackgroundImageAll::getUploadTime, pageVO.getUploadTime());
            }
            if(pageVO.getBackgroundImageId() != null){
                queryWrapper.eq(BackgroundImageAll::getBackgroundImageId, pageVO.getBackgroundImageId());
            }
        if(StringUtils.isNotEmpty(pageVO.getPassword())) {
                queryWrapper.like(BackgroundImageAll::getPassword, pageVO.getPassword());
            }
            if(pageVO.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(BackgroundImageAll::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
            }
            if(pageVO.getRegistrationDate() != null){
                queryWrapper.eq(BackgroundImageAll::getRegistrationDate, pageVO.getRegistrationDate());
            }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
                queryWrapper.like(BackgroundImageAll::getMajor, pageVO.getMajor());
            }
        if(StringUtils.isNotEmpty(pageVO.getName())) {

                        String s_string = pageVO.getName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(BackgroundImageAll::getName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
        if(StringUtils.isNotEmpty(pageVO.getStudentId())) {
                queryWrapper.like(BackgroundImageAll::getStudentId, pageVO.getStudentId());
            }
            if(pageVO.getRoleEnumRoleEnumId1() != null){
                queryWrapper.eq(BackgroundImageAll::getRoleEnumRoleEnumId1, pageVO.getRoleEnumRoleEnumId1());
            }
            if(pageVO.getUserStatusEnumUserStatusEnumId1() != null){
                queryWrapper.eq(BackgroundImageAll::getUserStatusEnumUserStatusEnumId1, pageVO.getUserStatusEnumUserStatusEnumId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
                queryWrapper.like(BackgroundImageAll::getPhoneNumber, pageVO.getPhoneNumber());
            }

                    backgroundImageAllService.pageNew(page, pageVO, queryWrapper);
                    page.getRecords().forEach(item -> {
                    });

        page.getRecords().forEach(item -> {
        });


            return ResultUtils.success(page);
        }

        public BaseResponse list(BackgroundImageAllPageVO vo){
            LambdaQueryWrapper<BackgroundImageAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCollege())) {
                    queryWrapper.eq(BackgroundImageAll::getCollege, vo.getCollege());
                }
                if(vo.getUploadTime() != null){
                    queryWrapper.eq(BackgroundImageAll::getUploadTime, vo.getUploadTime());
                }
            if(StringUtils.isNotEmpty(vo.getPassword())) {
                    queryWrapper.eq(BackgroundImageAll::getPassword, vo.getPassword());
                }
                if(vo.getUserInfoUserInfoId1() != null){
                    queryWrapper.eq(BackgroundImageAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                }
                if(vo.getRegistrationDate() != null){
                    queryWrapper.eq(BackgroundImageAll::getRegistrationDate, vo.getRegistrationDate());
                }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
                    queryWrapper.eq(BackgroundImageAll::getMajor, vo.getMajor());
                }
            if(StringUtils.isNotEmpty(vo.getName())) {
                            queryWrapper.like(BackgroundImageAll::getName, vo.getName());
                }
            if(StringUtils.isNotEmpty(vo.getStudentId())) {
                    queryWrapper.eq(BackgroundImageAll::getStudentId, vo.getStudentId());
                }
                if(vo.getRoleEnumRoleEnumId1() != null){
                    queryWrapper.eq(BackgroundImageAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                }
                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                    queryWrapper.eq(BackgroundImageAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                }
            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                    queryWrapper.eq(BackgroundImageAll::getPhoneNumber, vo.getPhoneNumber());
                }
                return ResultUtils.success(backgroundImageAllService.selectListNew(new PageRequest(),queryWrapper,false));
            }


            public BaseResponse get(BackgroundImageAllPageVO vo) {


                LambdaQueryWrapper<BackgroundImageAll> queryWrapper = new LambdaQueryWrapper<>();


                            if(StringUtils.isNotEmpty(vo.getCollege())) {
                                    queryWrapper.eq(BackgroundImageAll::getCollege, vo.getCollege());
                                }


                                if(vo.getUploadTime() != null){
                                    queryWrapper.eq(BackgroundImageAll::getUploadTime, vo.getUploadTime());
                                }


                                if(vo.getBackgroundImageId() != null){
                                    queryWrapper.eq(BackgroundImageAll::getBackgroundImageId, vo.getBackgroundImageId());
                                }


                            if(StringUtils.isNotEmpty(vo.getPassword())) {
                                    queryWrapper.eq(BackgroundImageAll::getPassword, vo.getPassword());
                                }


                                if(vo.getUserInfoUserInfoId1() != null){
                                    queryWrapper.eq(BackgroundImageAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                                }


                                if(vo.getRegistrationDate() != null){
                                    queryWrapper.eq(BackgroundImageAll::getRegistrationDate, vo.getRegistrationDate());
                                }


                            if(StringUtils.isNotEmpty(vo.getMajor())) {
                                    queryWrapper.eq(BackgroundImageAll::getMajor, vo.getMajor());
                                }


                            if(StringUtils.isNotEmpty(vo.getName())) {
                                            queryWrapper.like(BackgroundImageAll::getName, vo.getName());
                                }


                            if(StringUtils.isNotEmpty(vo.getStudentId())) {
                                    queryWrapper.eq(BackgroundImageAll::getStudentId, vo.getStudentId());
                                }


                                if(vo.getRoleEnumRoleEnumId1() != null){
                                    queryWrapper.eq(BackgroundImageAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                                }


                                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                                    queryWrapper.eq(BackgroundImageAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                                    queryWrapper.eq(BackgroundImageAll::getPhoneNumber, vo.getPhoneNumber());
                                }

                // BackgroundImageAll entity = this.backgroundImageAllService.getById(backgroundImageId);
                    final Page<BackgroundImageAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    backgroundImageAllService.pageNew(page, vo, queryWrapper);
                    final List<BackgroundImageAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? ResultUtils.success(records.get(0)) : ResultUtils.error(ErrorCode.OPERATION_ERROR);

                    // return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

            public BackgroundImageAll getById(Serializable backgroundImageId) {

                    LambdaQueryWrapper<BackgroundImageAll> queryWrapper = new LambdaQueryWrapper<>();

                            queryWrapper.eq(BackgroundImageAll::getBackgroundImageId, backgroundImageId);

                    final Page<BackgroundImageAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    backgroundImageAllService.pageNew(page, new PageRequest() , queryWrapper);
                    final List<BackgroundImageAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? records.get(0) : null;

            }


        }

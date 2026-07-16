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
import com.kuafu.web.entity.UserInfoAll;
import com.kuafu.web.service.IUserInfoAllService;
import com.kuafu.web.vo.UserInfoAllPageVO;
import com.kuafu.web.entity.SelectVo;
import com.kuafu.web.service.IRoleEnumService;
import com.kuafu.web.entity.RoleEnum;
import com.kuafu.web.service.IUserStatusEnumService;
import com.kuafu.web.entity.UserStatusEnum;
import com.kuafu.web.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuafu.common.util.QueryUtils;
import org.springframework.context.annotation.Lazy;





/**
 * <p> 用户信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("UserInfoAllControllerService")
public class UserInfoAllControllerService implements IControllerService<UserInfoAll> {


@Autowired
    private  IUserInfoAllService userInfoAllService;








    public BaseResponse page( UserInfoAllPageVO pageVO){
        IPage<UserInfoAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

        final QueryWrapper<UserInfoAll> objectQueryWrapper = new QueryWrapper<>();
        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        objectQueryWrapper.orderByDesc("ui.user_info_id");
        LambdaQueryWrapper<UserInfoAll> queryWrapper = objectQueryWrapper.lambda();


        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
                queryWrapper.like(UserInfoAll::getCollege, pageVO.getCollege());
            }
        if(StringUtils.isNotEmpty(pageVO.getRoleName())) {

                        String s_string = pageVO.getRoleName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(UserInfoAll::getRoleName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
        if(StringUtils.isNotEmpty(pageVO.getPassword())) {
                queryWrapper.like(UserInfoAll::getPassword, pageVO.getPassword());
            }
            if(pageVO.getRegistrationDate() != null){
                queryWrapper.eq(UserInfoAll::getRegistrationDate, pageVO.getRegistrationDate());
            }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
                queryWrapper.like(UserInfoAll::getMajor, pageVO.getMajor());
            }
        if(StringUtils.isNotEmpty(pageVO.getStatusName())) {

                        String s_string = pageVO.getStatusName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(UserInfoAll::getStatusName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
        if(StringUtils.isNotEmpty(pageVO.getName())) {

                        String s_string = pageVO.getName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(UserInfoAll::getName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
        if(StringUtils.isNotEmpty(pageVO.getStudentId())) {
                queryWrapper.like(UserInfoAll::getStudentId, pageVO.getStudentId());
            }
            if(pageVO.getRoleEnumRoleEnumId1() != null){
                queryWrapper.eq(UserInfoAll::getRoleEnumRoleEnumId1, pageVO.getRoleEnumRoleEnumId1());
            }
            if(pageVO.getUserStatusEnumUserStatusEnumId1() != null){
                queryWrapper.eq(UserInfoAll::getUserStatusEnumUserStatusEnumId1, pageVO.getUserStatusEnumUserStatusEnumId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
                queryWrapper.like(UserInfoAll::getPhoneNumber, pageVO.getPhoneNumber());
            }
            if(pageVO.getUserInfoId() != null){
                queryWrapper.eq(UserInfoAll::getUserInfoId, pageVO.getUserInfoId());
            }

                    userInfoAllService.pageNew(page, pageVO, queryWrapper);
                    page.getRecords().forEach(item -> {
                    });

        page.getRecords().forEach(item -> {
        });


            return ResultUtils.success(page);
        }

        public BaseResponse list(UserInfoAllPageVO vo){
            LambdaQueryWrapper<UserInfoAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCollege())) {
                    queryWrapper.eq(UserInfoAll::getCollege, vo.getCollege());
                }
            if(StringUtils.isNotEmpty(vo.getRoleName())) {
                            queryWrapper.like(UserInfoAll::getRoleName, vo.getRoleName());
                }
            if(StringUtils.isNotEmpty(vo.getPassword())) {
                    queryWrapper.eq(UserInfoAll::getPassword, vo.getPassword());
                }
                if(vo.getRegistrationDate() != null){
                    queryWrapper.eq(UserInfoAll::getRegistrationDate, vo.getRegistrationDate());
                }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
                    queryWrapper.eq(UserInfoAll::getMajor, vo.getMajor());
                }
            if(StringUtils.isNotEmpty(vo.getStatusName())) {
                            queryWrapper.like(UserInfoAll::getStatusName, vo.getStatusName());
                }
            if(StringUtils.isNotEmpty(vo.getName())) {
                            queryWrapper.like(UserInfoAll::getName, vo.getName());
                }
            if(StringUtils.isNotEmpty(vo.getStudentId())) {
                    queryWrapper.eq(UserInfoAll::getStudentId, vo.getStudentId());
                }
                if(vo.getRoleEnumRoleEnumId1() != null){
                    queryWrapper.eq(UserInfoAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                }
                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                    queryWrapper.eq(UserInfoAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                }
            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                    queryWrapper.eq(UserInfoAll::getPhoneNumber, vo.getPhoneNumber());
                }
                return ResultUtils.success(userInfoAllService.selectListNew(new PageRequest(),queryWrapper,false));
            }


            public BaseResponse get(UserInfoAllPageVO vo) {


                LambdaQueryWrapper<UserInfoAll> queryWrapper = new LambdaQueryWrapper<>();


                            if(StringUtils.isNotEmpty(vo.getCollege())) {
                                    queryWrapper.eq(UserInfoAll::getCollege, vo.getCollege());
                                }


                            if(StringUtils.isNotEmpty(vo.getRoleName())) {
                                            queryWrapper.like(UserInfoAll::getRoleName, vo.getRoleName());
                                }


                            if(StringUtils.isNotEmpty(vo.getPassword())) {
                                    queryWrapper.eq(UserInfoAll::getPassword, vo.getPassword());
                                }


                                if(vo.getRegistrationDate() != null){
                                    queryWrapper.eq(UserInfoAll::getRegistrationDate, vo.getRegistrationDate());
                                }


                            if(StringUtils.isNotEmpty(vo.getMajor())) {
                                    queryWrapper.eq(UserInfoAll::getMajor, vo.getMajor());
                                }


                            if(StringUtils.isNotEmpty(vo.getStatusName())) {
                                            queryWrapper.like(UserInfoAll::getStatusName, vo.getStatusName());
                                }


                            if(StringUtils.isNotEmpty(vo.getName())) {
                                            queryWrapper.like(UserInfoAll::getName, vo.getName());
                                }


                            if(StringUtils.isNotEmpty(vo.getStudentId())) {
                                    queryWrapper.eq(UserInfoAll::getStudentId, vo.getStudentId());
                                }


                                if(vo.getRoleEnumRoleEnumId1() != null){
                                    queryWrapper.eq(UserInfoAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                                }


                                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                                    queryWrapper.eq(UserInfoAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                                    queryWrapper.eq(UserInfoAll::getPhoneNumber, vo.getPhoneNumber());
                                }


                                if(vo.getUserInfoId() != null){
                                    queryWrapper.eq(UserInfoAll::getUserInfoId, vo.getUserInfoId());
                                }

                // UserInfoAll entity = this.userInfoAllService.getById(userInfoId);
                    final Page<UserInfoAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    userInfoAllService.pageNew(page, vo, queryWrapper);
                    final List<UserInfoAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? ResultUtils.success(records.get(0)) : ResultUtils.error(ErrorCode.OPERATION_ERROR);

                    // return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

            public UserInfoAll getById(Serializable userInfoId) {

                    LambdaQueryWrapper<UserInfoAll> queryWrapper = new LambdaQueryWrapper<>();

                            queryWrapper.eq(UserInfoAll::getUserInfoId, userInfoId);

                    final Page<UserInfoAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    userInfoAllService.pageNew(page, new PageRequest() , queryWrapper);
                    final List<UserInfoAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? records.get(0) : null;

            }


        }

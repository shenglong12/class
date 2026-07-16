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
import com.kuafu.web.entity.RepairOrderAll;
import com.kuafu.web.service.IRepairOrderAllService;
import com.kuafu.web.vo.RepairOrderAllPageVO;
import com.kuafu.web.entity.SelectVo;
import com.kuafu.web.service.IUserInfoService;
import com.kuafu.web.entity.UserInfo;
import com.kuafu.web.service.IClassroomInfoService;
import com.kuafu.web.entity.ClassroomInfo;
import com.kuafu.web.service.IRepairStatusEnumService;
import com.kuafu.web.entity.RepairStatusEnum;
import com.kuafu.web.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuafu.common.util.QueryUtils;
import org.springframework.context.annotation.Lazy;





/**
 * <p> 报修单 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("RepairOrderAllControllerService")
public class RepairOrderAllControllerService implements IControllerService<RepairOrderAll> {


@Autowired
    private  IRepairOrderAllService repairOrderAllService;








    public BaseResponse page( RepairOrderAllPageVO pageVO){
        IPage<RepairOrderAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

        final QueryWrapper<RepairOrderAll> objectQueryWrapper = new QueryWrapper<>();
        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        objectQueryWrapper.orderByDesc("ro.repair_order_id");
        LambdaQueryWrapper<RepairOrderAll> queryWrapper = objectQueryWrapper.lambda();


        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
                queryWrapper.like(RepairOrderAll::getCollege, pageVO.getCollege());
            }
            if(pageVO.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(RepairOrderAll::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
            }
            if(pageVO.getCreateTime() != null){
                queryWrapper.eq(RepairOrderAll::getCreateTime, pageVO.getCreateTime());
            }
            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
                queryWrapper.eq(RepairOrderAll::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getStatusName())) {

                        String s_string = pageVO.getStatusName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(RepairOrderAll::getStatusName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
        if(StringUtils.isNotEmpty(pageVO.getStudentId())) {
                queryWrapper.like(RepairOrderAll::getStudentId, pageVO.getStudentId());
            }
            if(pageVO.getRoleEnumRoleEnumId1() != null){
                queryWrapper.eq(RepairOrderAll::getRoleEnumRoleEnumId1, pageVO.getRoleEnumRoleEnumId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
                queryWrapper.like(RepairOrderAll::getRoomNumber, pageVO.getRoomNumber());
            }
        if(StringUtils.isNotEmpty(pageVO.getQrCodePath())) {
                queryWrapper.like(RepairOrderAll::getQrCodePath, pageVO.getQrCodePath());
            }
        if(StringUtils.isNotEmpty(pageVO.getContent())) {
                queryWrapper.like(RepairOrderAll::getContent, pageVO.getContent());
            }
            if(pageVO.getRepairOrderId() != null){
                queryWrapper.eq(RepairOrderAll::getRepairOrderId, pageVO.getRepairOrderId());
            }
        if(StringUtils.isNotEmpty(pageVO.getFeedback())) {
                queryWrapper.like(RepairOrderAll::getFeedback, pageVO.getFeedback());
            }
        if(StringUtils.isNotEmpty(pageVO.getPassword())) {
                queryWrapper.like(RepairOrderAll::getPassword, pageVO.getPassword());
            }
            if(pageVO.getBuildingEnumBuildingEnumId1() != null){
                queryWrapper.eq(RepairOrderAll::getBuildingEnumBuildingEnumId1, pageVO.getBuildingEnumBuildingEnumId1());
            }
            if(pageVO.getRegistrationDate() != null){
                queryWrapper.eq(RepairOrderAll::getRegistrationDate, pageVO.getRegistrationDate());
            }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
                queryWrapper.like(RepairOrderAll::getMajor, pageVO.getMajor());
            }
        if(StringUtils.isNotEmpty(pageVO.getName())) {

                        String s_string = pageVO.getName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(RepairOrderAll::getName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
            if(pageVO.getUserStatusEnumUserStatusEnumId1() != null){
                queryWrapper.eq(RepairOrderAll::getUserStatusEnumUserStatusEnumId1, pageVO.getUserStatusEnumUserStatusEnumId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
                queryWrapper.like(RepairOrderAll::getPhoneNumber, pageVO.getPhoneNumber());
            }
            if(pageVO.getRepairStatusEnumRepairStatusEnumId1() != null){
                queryWrapper.eq(RepairOrderAll::getRepairStatusEnumRepairStatusEnumId1, pageVO.getRepairStatusEnumRepairStatusEnumId1());
            }

                    repairOrderAllService.pageNew(page, pageVO, queryWrapper);
                    page.getRecords().forEach(item -> {
                    });

        page.getRecords().forEach(item -> {
        });


            return ResultUtils.success(page);
        }

        public BaseResponse list(RepairOrderAllPageVO vo){
            LambdaQueryWrapper<RepairOrderAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCollege())) {
                    queryWrapper.eq(RepairOrderAll::getCollege, vo.getCollege());
                }
                if(vo.getUserInfoUserInfoId1() != null){
                    queryWrapper.eq(RepairOrderAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                }
                if(vo.getCreateTime() != null){
                    queryWrapper.eq(RepairOrderAll::getCreateTime, vo.getCreateTime());
                }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
                    queryWrapper.eq(RepairOrderAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
                }
            if(StringUtils.isNotEmpty(vo.getStatusName())) {
                            queryWrapper.like(RepairOrderAll::getStatusName, vo.getStatusName());
                }
            if(StringUtils.isNotEmpty(vo.getStudentId())) {
                    queryWrapper.eq(RepairOrderAll::getStudentId, vo.getStudentId());
                }
                if(vo.getRoleEnumRoleEnumId1() != null){
                    queryWrapper.eq(RepairOrderAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                }
            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                    queryWrapper.eq(RepairOrderAll::getRoomNumber, vo.getRoomNumber());
                }
            if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
                    queryWrapper.eq(RepairOrderAll::getQrCodePath, vo.getQrCodePath());
                }
            if(StringUtils.isNotEmpty(vo.getContent())) {
                    queryWrapper.eq(RepairOrderAll::getContent, vo.getContent());
                }
            if(StringUtils.isNotEmpty(vo.getFeedback())) {
                    queryWrapper.eq(RepairOrderAll::getFeedback, vo.getFeedback());
                }
            if(StringUtils.isNotEmpty(vo.getPassword())) {
                    queryWrapper.eq(RepairOrderAll::getPassword, vo.getPassword());
                }
                if(vo.getBuildingEnumBuildingEnumId1() != null){
                    queryWrapper.eq(RepairOrderAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                }
                if(vo.getRegistrationDate() != null){
                    queryWrapper.eq(RepairOrderAll::getRegistrationDate, vo.getRegistrationDate());
                }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
                    queryWrapper.eq(RepairOrderAll::getMajor, vo.getMajor());
                }
            if(StringUtils.isNotEmpty(vo.getName())) {
                            queryWrapper.like(RepairOrderAll::getName, vo.getName());
                }
                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                    queryWrapper.eq(RepairOrderAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                }
            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                    queryWrapper.eq(RepairOrderAll::getPhoneNumber, vo.getPhoneNumber());
                }
                if(vo.getRepairStatusEnumRepairStatusEnumId1() != null){
                    queryWrapper.eq(RepairOrderAll::getRepairStatusEnumRepairStatusEnumId1, vo.getRepairStatusEnumRepairStatusEnumId1());
                }
                return ResultUtils.success(repairOrderAllService.selectListNew(new PageRequest(),queryWrapper,false));
            }


            public BaseResponse get(RepairOrderAllPageVO vo) {


                LambdaQueryWrapper<RepairOrderAll> queryWrapper = new LambdaQueryWrapper<>();


                            if(StringUtils.isNotEmpty(vo.getCollege())) {
                                    queryWrapper.eq(RepairOrderAll::getCollege, vo.getCollege());
                                }


                                if(vo.getUserInfoUserInfoId1() != null){
                                    queryWrapper.eq(RepairOrderAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                                }


                                if(vo.getCreateTime() != null){
                                    queryWrapper.eq(RepairOrderAll::getCreateTime, vo.getCreateTime());
                                }


                                if(vo.getClassroomInfoClassroomInfoId1() != null){
                                    queryWrapper.eq(RepairOrderAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getStatusName())) {
                                            queryWrapper.like(RepairOrderAll::getStatusName, vo.getStatusName());
                                }


                            if(StringUtils.isNotEmpty(vo.getStudentId())) {
                                    queryWrapper.eq(RepairOrderAll::getStudentId, vo.getStudentId());
                                }


                                if(vo.getRoleEnumRoleEnumId1() != null){
                                    queryWrapper.eq(RepairOrderAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                                    queryWrapper.eq(RepairOrderAll::getRoomNumber, vo.getRoomNumber());
                                }


                            if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
                                    queryWrapper.eq(RepairOrderAll::getQrCodePath, vo.getQrCodePath());
                                }


                            if(StringUtils.isNotEmpty(vo.getContent())) {
                                    queryWrapper.eq(RepairOrderAll::getContent, vo.getContent());
                                }


                                if(vo.getRepairOrderId() != null){
                                    queryWrapper.eq(RepairOrderAll::getRepairOrderId, vo.getRepairOrderId());
                                }


                            if(StringUtils.isNotEmpty(vo.getFeedback())) {
                                    queryWrapper.eq(RepairOrderAll::getFeedback, vo.getFeedback());
                                }


                            if(StringUtils.isNotEmpty(vo.getPassword())) {
                                    queryWrapper.eq(RepairOrderAll::getPassword, vo.getPassword());
                                }


                                if(vo.getBuildingEnumBuildingEnumId1() != null){
                                    queryWrapper.eq(RepairOrderAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                                }


                                if(vo.getRegistrationDate() != null){
                                    queryWrapper.eq(RepairOrderAll::getRegistrationDate, vo.getRegistrationDate());
                                }


                            if(StringUtils.isNotEmpty(vo.getMajor())) {
                                    queryWrapper.eq(RepairOrderAll::getMajor, vo.getMajor());
                                }


                            if(StringUtils.isNotEmpty(vo.getName())) {
                                            queryWrapper.like(RepairOrderAll::getName, vo.getName());
                                }


                                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                                    queryWrapper.eq(RepairOrderAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                                    queryWrapper.eq(RepairOrderAll::getPhoneNumber, vo.getPhoneNumber());
                                }


                                if(vo.getRepairStatusEnumRepairStatusEnumId1() != null){
                                    queryWrapper.eq(RepairOrderAll::getRepairStatusEnumRepairStatusEnumId1, vo.getRepairStatusEnumRepairStatusEnumId1());
                                }

                // RepairOrderAll entity = this.repairOrderAllService.getById(repairOrderId);
                    final Page<RepairOrderAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    repairOrderAllService.pageNew(page, vo, queryWrapper);
                    final List<RepairOrderAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? ResultUtils.success(records.get(0)) : ResultUtils.error(ErrorCode.OPERATION_ERROR);

                    // return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

            public RepairOrderAll getById(Serializable repairOrderId) {

                    LambdaQueryWrapper<RepairOrderAll> queryWrapper = new LambdaQueryWrapper<>();

                            queryWrapper.eq(RepairOrderAll::getRepairOrderId, repairOrderId);

                    final Page<RepairOrderAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    repairOrderAllService.pageNew(page, new PageRequest() , queryWrapper);
                    final List<RepairOrderAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? records.get(0) : null;

            }


        }

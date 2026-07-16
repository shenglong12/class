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
import com.kuafu.web.entity.ScanRecordAll;
import com.kuafu.web.service.IScanRecordAllService;
import com.kuafu.web.vo.ScanRecordAllPageVO;
import com.kuafu.web.entity.SelectVo;
import com.kuafu.web.service.IUserInfoService;
import com.kuafu.web.entity.UserInfo;
import com.kuafu.web.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuafu.common.util.QueryUtils;
import org.springframework.context.annotation.Lazy;





/**
 * <p> 扫码记录 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("ScanRecordAllControllerService")
public class ScanRecordAllControllerService implements IControllerService<ScanRecordAll> {


@Autowired
    private  IScanRecordAllService scanRecordAllService;








    public BaseResponse page( ScanRecordAllPageVO pageVO){
        IPage<ScanRecordAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

        final QueryWrapper<ScanRecordAll> objectQueryWrapper = new QueryWrapper<>();
        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        objectQueryWrapper.orderByDesc("sr.scan_id");
        LambdaQueryWrapper<ScanRecordAll> queryWrapper = objectQueryWrapper.lambda();


        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
                queryWrapper.like(ScanRecordAll::getCollege, pageVO.getCollege());
            }
        if(StringUtils.isNotEmpty(pageVO.getBuildingName())) {

                        String s_string = pageVO.getBuildingName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(ScanRecordAll::getBuildingName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
            if(pageVO.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(ScanRecordAll::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
                queryWrapper.like(ScanRecordAll::getRoomNumber, pageVO.getRoomNumber());
            }
        if(StringUtils.isNotEmpty(pageVO.getStudentId())) {
                queryWrapper.like(ScanRecordAll::getStudentId, pageVO.getStudentId());
            }
            if(pageVO.getRoleEnumRoleEnumId1() != null){
                queryWrapper.eq(ScanRecordAll::getRoleEnumRoleEnumId1, pageVO.getRoleEnumRoleEnumId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getPassword())) {
                queryWrapper.like(ScanRecordAll::getPassword, pageVO.getPassword());
            }
            if(pageVO.getRegistrationDate() != null){
                queryWrapper.eq(ScanRecordAll::getRegistrationDate, pageVO.getRegistrationDate());
            }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
                queryWrapper.like(ScanRecordAll::getMajor, pageVO.getMajor());
            }
        if(StringUtils.isNotEmpty(pageVO.getName())) {

                        String s_string = pageVO.getName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(ScanRecordAll::getName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }
            if(pageVO.getUserStatusEnumUserStatusEnumId1() != null){
                queryWrapper.eq(ScanRecordAll::getUserStatusEnumUserStatusEnumId1, pageVO.getUserStatusEnumUserStatusEnumId1());
            }
        if(StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
                queryWrapper.like(ScanRecordAll::getPhoneNumber, pageVO.getPhoneNumber());
            }
            if(pageVO.getScanId() != null){
                queryWrapper.eq(ScanRecordAll::getScanId, pageVO.getScanId());
            }
            if(pageVO.getScanTime() != null){
                queryWrapper.eq(ScanRecordAll::getScanTime, pageVO.getScanTime());
            }

                    scanRecordAllService.pageNew(page, pageVO, queryWrapper);
                    page.getRecords().forEach(item -> {
                    });

        page.getRecords().forEach(item -> {
        });


            return ResultUtils.success(page);
        }

        public BaseResponse list(ScanRecordAllPageVO vo){
            LambdaQueryWrapper<ScanRecordAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCollege())) {
                    queryWrapper.eq(ScanRecordAll::getCollege, vo.getCollege());
                }
            if(StringUtils.isNotEmpty(vo.getBuildingName())) {
                            queryWrapper.like(ScanRecordAll::getBuildingName, vo.getBuildingName());
                }
                if(vo.getUserInfoUserInfoId1() != null){
                    queryWrapper.eq(ScanRecordAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                }
            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                    queryWrapper.eq(ScanRecordAll::getRoomNumber, vo.getRoomNumber());
                }
            if(StringUtils.isNotEmpty(vo.getStudentId())) {
                    queryWrapper.eq(ScanRecordAll::getStudentId, vo.getStudentId());
                }
                if(vo.getRoleEnumRoleEnumId1() != null){
                    queryWrapper.eq(ScanRecordAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                }
            if(StringUtils.isNotEmpty(vo.getPassword())) {
                    queryWrapper.eq(ScanRecordAll::getPassword, vo.getPassword());
                }
                if(vo.getRegistrationDate() != null){
                    queryWrapper.eq(ScanRecordAll::getRegistrationDate, vo.getRegistrationDate());
                }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
                    queryWrapper.eq(ScanRecordAll::getMajor, vo.getMajor());
                }
            if(StringUtils.isNotEmpty(vo.getName())) {
                            queryWrapper.like(ScanRecordAll::getName, vo.getName());
                }
                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                    queryWrapper.eq(ScanRecordAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                }
            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                    queryWrapper.eq(ScanRecordAll::getPhoneNumber, vo.getPhoneNumber());
                }
                if(vo.getScanTime() != null){
                    queryWrapper.eq(ScanRecordAll::getScanTime, vo.getScanTime());
                }
                return ResultUtils.success(scanRecordAllService.selectListNew(new PageRequest(),queryWrapper,false));
            }


            public BaseResponse get(ScanRecordAllPageVO vo) {


                LambdaQueryWrapper<ScanRecordAll> queryWrapper = new LambdaQueryWrapper<>();


                            if(StringUtils.isNotEmpty(vo.getCollege())) {
                                    queryWrapper.eq(ScanRecordAll::getCollege, vo.getCollege());
                                }


                            if(StringUtils.isNotEmpty(vo.getBuildingName())) {
                                            queryWrapper.like(ScanRecordAll::getBuildingName, vo.getBuildingName());
                                }


                                if(vo.getUserInfoUserInfoId1() != null){
                                    queryWrapper.eq(ScanRecordAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                                    queryWrapper.eq(ScanRecordAll::getRoomNumber, vo.getRoomNumber());
                                }


                            if(StringUtils.isNotEmpty(vo.getStudentId())) {
                                    queryWrapper.eq(ScanRecordAll::getStudentId, vo.getStudentId());
                                }


                                if(vo.getRoleEnumRoleEnumId1() != null){
                                    queryWrapper.eq(ScanRecordAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getPassword())) {
                                    queryWrapper.eq(ScanRecordAll::getPassword, vo.getPassword());
                                }


                                if(vo.getRegistrationDate() != null){
                                    queryWrapper.eq(ScanRecordAll::getRegistrationDate, vo.getRegistrationDate());
                                }


                            if(StringUtils.isNotEmpty(vo.getMajor())) {
                                    queryWrapper.eq(ScanRecordAll::getMajor, vo.getMajor());
                                }


                            if(StringUtils.isNotEmpty(vo.getName())) {
                                            queryWrapper.like(ScanRecordAll::getName, vo.getName());
                                }


                                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                                    queryWrapper.eq(ScanRecordAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                                }


                            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                                    queryWrapper.eq(ScanRecordAll::getPhoneNumber, vo.getPhoneNumber());
                                }


                                if(vo.getScanId() != null){
                                    queryWrapper.eq(ScanRecordAll::getScanId, vo.getScanId());
                                }


                                if(vo.getScanTime() != null){
                                    queryWrapper.eq(ScanRecordAll::getScanTime, vo.getScanTime());
                                }

                // ScanRecordAll entity = this.scanRecordAllService.getById(scanId);
                    final Page<ScanRecordAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    scanRecordAllService.pageNew(page, vo, queryWrapper);
                    final List<ScanRecordAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? ResultUtils.success(records.get(0)) : ResultUtils.error(ErrorCode.OPERATION_ERROR);

                    // return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

            public ScanRecordAll getById(Serializable scanId) {

                    LambdaQueryWrapper<ScanRecordAll> queryWrapper = new LambdaQueryWrapper<>();

                            queryWrapper.eq(ScanRecordAll::getScanId, scanId);

                    final Page<ScanRecordAll> page = new Page<>();
                    page.setCurrent(1);
                    page.setSize(1);
                    scanRecordAllService.pageNew(page, new PageRequest() , queryWrapper);
                    final List<ScanRecordAll> records = page.getRecords();
                    return ObjectUtils.isNotEmpty(records) ? records.get(0) : null;

            }


        }

package com.kuafu.web.controller;

import com.kuafu.common.util.poi.ExcelUtil;
import java.util.List;
import java.util.stream.Collectors;
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
import javax.servlet.http.HttpServletResponse;


import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.web.entity.SelectVO;
import com.kuafu.web.entity.ScanRecordAll;
import com.kuafu.web.service.IScanRecordAllService;
import com.kuafu.web.vo.ScanRecordAllPageVO;

import com.kuafu.web.service.IUserInfoService;
import com.kuafu.web.entity.UserInfo;


/**
 * <p> 扫码记录 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/scanRecordAll")
@Api(tags = {"扫码记录"})
public class ScanRecordAllController  {

    private final IScanRecordAllService scanRecordAllService;

    @PostMapping("page")
    @ApiOperation("全字段分页")
    public BaseResponse page(@RequestBody ScanRecordAllPageVO pageVO){
        IPage<ScanRecordAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<ScanRecordAll> queryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
            queryWrapper.eq(ScanRecordAll::getCollege, pageVO.getCollege());
        }
            if(pageVO.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(ScanRecordAll::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getBuildingName())) {
            queryWrapper.like(ScanRecordAll::getBuildingName, pageVO.getBuildingName());
        }
            if(pageVO.getRoleEnumRoleEnumId1() != null){
            queryWrapper.eq(ScanRecordAll::getRoleEnumRoleEnumId1, pageVO.getRoleEnumRoleEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
            queryWrapper.eq(ScanRecordAll::getRoomNumber, pageVO.getRoomNumber());
        }
        if(StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
            queryWrapper.eq(ScanRecordAll::getPhoneNumber, pageVO.getPhoneNumber());
        }
            if(pageVO.getRegistrationDate() != null){
            queryWrapper.eq(ScanRecordAll::getRegistrationDate, pageVO.getRegistrationDate());
        }
            if(pageVO.getUserStatusEnumUserStatusEnumId1() != null){
            queryWrapper.eq(ScanRecordAll::getUserStatusEnumUserStatusEnumId1, pageVO.getUserStatusEnumUserStatusEnumId1());
        }
            if(pageVO.getScanTime() != null){
            queryWrapper.eq(ScanRecordAll::getScanTime, pageVO.getScanTime());
        }
        if(StringUtils.isNotEmpty(pageVO.getStudentId())) {
            queryWrapper.eq(ScanRecordAll::getStudentId, pageVO.getStudentId());
        }
        if(StringUtils.isNotEmpty(pageVO.getPassword())) {
            queryWrapper.eq(ScanRecordAll::getPassword, pageVO.getPassword());
        }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
            queryWrapper.eq(ScanRecordAll::getMajor, pageVO.getMajor());
        }
        if(StringUtils.isNotEmpty(pageVO.getName())) {
            queryWrapper.like(ScanRecordAll::getName, pageVO.getName());
        }
        return ResultUtils.success(scanRecordAllService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("全字段列表")
    public BaseResponse list(@RequestBody ScanRecordAllPageVO vo){
        LambdaQueryWrapper<ScanRecordAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCollege())) {
            queryWrapper.eq(ScanRecordAll::getCollege, vo.getCollege());
        }
                if(vo.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(ScanRecordAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
        }
            if(StringUtils.isNotEmpty(vo.getBuildingName())) {
            queryWrapper.like(ScanRecordAll::getBuildingName, vo.getBuildingName());
        }
                if(vo.getRoleEnumRoleEnumId1() != null){
            queryWrapper.eq(ScanRecordAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
            queryWrapper.eq(ScanRecordAll::getRoomNumber, vo.getRoomNumber());
        }
            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
            queryWrapper.eq(ScanRecordAll::getPhoneNumber, vo.getPhoneNumber());
        }
                if(vo.getRegistrationDate() != null){
            queryWrapper.eq(ScanRecordAll::getRegistrationDate, vo.getRegistrationDate());
        }
                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
            queryWrapper.eq(ScanRecordAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
        }
                if(vo.getScanTime() != null){
            queryWrapper.eq(ScanRecordAll::getScanTime, vo.getScanTime());
        }
            if(StringUtils.isNotEmpty(vo.getStudentId())) {
            queryWrapper.eq(ScanRecordAll::getStudentId, vo.getStudentId());
        }
            if(StringUtils.isNotEmpty(vo.getPassword())) {
            queryWrapper.eq(ScanRecordAll::getPassword, vo.getPassword());
        }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
            queryWrapper.eq(ScanRecordAll::getMajor, vo.getMajor());
        }
            if(StringUtils.isNotEmpty(vo.getName())) {
            queryWrapper.like(ScanRecordAll::getName, vo.getName());
        }
        return ResultUtils.success(scanRecordAllService.list(queryWrapper));
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer scanId) {
        ScanRecordAll entity = this.scanRecordAllService.getById(scanId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


        private final IUserInfoService userInfoService;

        @GetMapping("get/user_info_list")
        @ApiOperation("查询下拉框的外键信息")
        public BaseResponse get_user_info_list() {
            List<UserInfo> list = this.userInfoService.list();
            final List<SelectVO> selectVoList = list.stream().map(p ->
                new SelectVO(p.getUserInfoId(), p.getName().toString()))
                        .collect(Collectors.toList());
            return ResultUtils.success(selectVoList);
        }


        /**
         * 导出excel
         */
        @PostMapping("/export")
        public void export(HttpServletResponse response, @RequestBody ScanRecordAllPageVO vo) {

            LambdaQueryWrapper<ScanRecordAll> queryWrapper = new LambdaQueryWrapper<>();

                if(StringUtils.isNotEmpty(vo.getCollege())) {
                        queryWrapper.eq(ScanRecordAll::getCollege, vo.getCollege());
                    }
                    if(vo.getUserInfoUserInfoId1() != null){
                        queryWrapper.eq(ScanRecordAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                    }
                if(StringUtils.isNotEmpty(vo.getBuildingName())) {
                                queryWrapper.like(ScanRecordAll::getBuildingName, vo.getBuildingName());
                    }
                    if(vo.getRoleEnumRoleEnumId1() != null){
                        queryWrapper.eq(ScanRecordAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                    }
                if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                        queryWrapper.eq(ScanRecordAll::getRoomNumber, vo.getRoomNumber());
                    }
                if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                        queryWrapper.eq(ScanRecordAll::getPhoneNumber, vo.getPhoneNumber());
                    }
                    if(vo.getRegistrationDate() != null){
                        queryWrapper.eq(ScanRecordAll::getRegistrationDate, vo.getRegistrationDate());
                    }
                    if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                        queryWrapper.eq(ScanRecordAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                    }
                    if(vo.getScanTime() != null){
                        queryWrapper.eq(ScanRecordAll::getScanTime, vo.getScanTime());
                    }
                if(StringUtils.isNotEmpty(vo.getStudentId())) {
                        queryWrapper.eq(ScanRecordAll::getStudentId, vo.getStudentId());
                    }
                if(StringUtils.isNotEmpty(vo.getPassword())) {
                        queryWrapper.eq(ScanRecordAll::getPassword, vo.getPassword());
                    }
                if(StringUtils.isNotEmpty(vo.getMajor())) {
                        queryWrapper.eq(ScanRecordAll::getMajor, vo.getMajor());
                    }
                if(StringUtils.isNotEmpty(vo.getName())) {
                                queryWrapper.like(ScanRecordAll::getName, vo.getName());
                    }
            List<ScanRecordAll> list = scanRecordAllService.list(queryWrapper);
            ExcelUtil<ScanRecordAll> util = new ExcelUtil<>(ScanRecordAll.class);
            util.exportExcel(response, list, "数据");
        }


}

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
import com.kuafu.web.entity.RepairOrderAll;
import com.kuafu.web.service.IRepairOrderAllService;
import com.kuafu.web.vo.RepairOrderAllPageVO;

import com.kuafu.web.service.IUserInfoService;
import com.kuafu.web.entity.UserInfo;
import com.kuafu.web.service.IClassroomInfoService;
import com.kuafu.web.entity.ClassroomInfo;
import com.kuafu.web.service.IRepairStatusEnumService;
import com.kuafu.web.entity.RepairStatusEnum;


/**
 * <p> 报修单 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/repairOrderAll")
@Api(tags = {"报修单"})
public class RepairOrderAllController  {

    private final IRepairOrderAllService repairOrderAllService;

    @PostMapping("page")
    @ApiOperation("全字段分页")
    public BaseResponse page(@RequestBody RepairOrderAllPageVO pageVO){
        IPage<RepairOrderAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<RepairOrderAll> queryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
            queryWrapper.eq(RepairOrderAll::getCollege, pageVO.getCollege());
        }
            if(pageVO.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(RepairOrderAll::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
        }
            if(pageVO.getCreateTime() != null){
            queryWrapper.eq(RepairOrderAll::getCreateTime, pageVO.getCreateTime());
        }
            if(pageVO.getRoleEnumRoleEnumId1() != null){
            queryWrapper.eq(RepairOrderAll::getRoleEnumRoleEnumId1, pageVO.getRoleEnumRoleEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
            queryWrapper.eq(RepairOrderAll::getRoomNumber, pageVO.getRoomNumber());
        }
        if(StringUtils.isNotEmpty(pageVO.getQrCodePath())) {
            queryWrapper.eq(RepairOrderAll::getQrCodePath, pageVO.getQrCodePath());
        }
        if(StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
            queryWrapper.eq(RepairOrderAll::getPhoneNumber, pageVO.getPhoneNumber());
        }
        if(StringUtils.isNotEmpty(pageVO.getStatusName())) {
            queryWrapper.like(RepairOrderAll::getStatusName, pageVO.getStatusName());
        }
            if(pageVO.getRegistrationDate() != null){
            queryWrapper.eq(RepairOrderAll::getRegistrationDate, pageVO.getRegistrationDate());
        }
        if(StringUtils.isNotEmpty(pageVO.getContent())) {
            queryWrapper.eq(RepairOrderAll::getContent, pageVO.getContent());
        }
            if(pageVO.getUserStatusEnumUserStatusEnumId1() != null){
            queryWrapper.eq(RepairOrderAll::getUserStatusEnumUserStatusEnumId1, pageVO.getUserStatusEnumUserStatusEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getFeedback())) {
            queryWrapper.eq(RepairOrderAll::getFeedback, pageVO.getFeedback());
        }
        if(StringUtils.isNotEmpty(pageVO.getStudentId())) {
            queryWrapper.eq(RepairOrderAll::getStudentId, pageVO.getStudentId());
        }
        if(StringUtils.isNotEmpty(pageVO.getPassword())) {
            queryWrapper.eq(RepairOrderAll::getPassword, pageVO.getPassword());
        }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
            queryWrapper.eq(RepairOrderAll::getMajor, pageVO.getMajor());
        }
            if(pageVO.getBuildingEnumBuildingEnumId1() != null){
            queryWrapper.eq(RepairOrderAll::getBuildingEnumBuildingEnumId1, pageVO.getBuildingEnumBuildingEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getImageFilesResourceKey())) {
            queryWrapper.eq(RepairOrderAll::getImageFilesResourceKey, pageVO.getImageFilesResourceKey());
        }
        if(StringUtils.isNotEmpty(pageVO.getName())) {
            queryWrapper.like(RepairOrderAll::getName, pageVO.getName());
        }
            if(pageVO.getRepairStatusEnumRepairStatusEnumId1() != null){
            queryWrapper.eq(RepairOrderAll::getRepairStatusEnumRepairStatusEnumId1, pageVO.getRepairStatusEnumRepairStatusEnumId1());
        }
            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(RepairOrderAll::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
        }
        // 按创建时间倒序排序，最新的报修单排在前面
        queryWrapper.orderByDesc(RepairOrderAll::getCreateTime);
        return ResultUtils.success(repairOrderAllService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("全字段列表")
    public BaseResponse list(@RequestBody RepairOrderAllPageVO vo){
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
                if(vo.getRoleEnumRoleEnumId1() != null){
            queryWrapper.eq(RepairOrderAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
            queryWrapper.eq(RepairOrderAll::getRoomNumber, vo.getRoomNumber());
        }
            if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
            queryWrapper.eq(RepairOrderAll::getQrCodePath, vo.getQrCodePath());
        }
            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
            queryWrapper.eq(RepairOrderAll::getPhoneNumber, vo.getPhoneNumber());
        }
            if(StringUtils.isNotEmpty(vo.getStatusName())) {
            queryWrapper.like(RepairOrderAll::getStatusName, vo.getStatusName());
        }
                if(vo.getRegistrationDate() != null){
            queryWrapper.eq(RepairOrderAll::getRegistrationDate, vo.getRegistrationDate());
        }
            if(StringUtils.isNotEmpty(vo.getContent())) {
            queryWrapper.eq(RepairOrderAll::getContent, vo.getContent());
        }
                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
            queryWrapper.eq(RepairOrderAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getFeedback())) {
            queryWrapper.eq(RepairOrderAll::getFeedback, vo.getFeedback());
        }
            if(StringUtils.isNotEmpty(vo.getStudentId())) {
            queryWrapper.eq(RepairOrderAll::getStudentId, vo.getStudentId());
        }
            if(StringUtils.isNotEmpty(vo.getPassword())) {
            queryWrapper.eq(RepairOrderAll::getPassword, vo.getPassword());
        }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
            queryWrapper.eq(RepairOrderAll::getMajor, vo.getMajor());
        }
                if(vo.getBuildingEnumBuildingEnumId1() != null){
            queryWrapper.eq(RepairOrderAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getImageFilesResourceKey())) {
            queryWrapper.eq(RepairOrderAll::getImageFilesResourceKey, vo.getImageFilesResourceKey());
        }
            if(StringUtils.isNotEmpty(vo.getName())) {
            queryWrapper.like(RepairOrderAll::getName, vo.getName());
        }
                if(vo.getRepairStatusEnumRepairStatusEnumId1() != null){
            queryWrapper.eq(RepairOrderAll::getRepairStatusEnumRepairStatusEnumId1, vo.getRepairStatusEnumRepairStatusEnumId1());
        }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(RepairOrderAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
        }
        return ResultUtils.success(repairOrderAllService.list(queryWrapper));
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer repairOrderId) {
        RepairOrderAll entity = this.repairOrderAllService.getById(repairOrderId);
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
        private final IClassroomInfoService classroomInfoService;

        @GetMapping("get/classroom_info_list")
        @ApiOperation("查询下拉框的外键信息")
        public BaseResponse get_classroom_info_list() {
            List<ClassroomInfo> list = this.classroomInfoService.list();
            final List<SelectVO> selectVoList = list.stream().map(p ->
                new SelectVO(p.getClassroomInfoId(), p.getRoomNumber().toString()))
                        .collect(Collectors.toList());
            return ResultUtils.success(selectVoList);
        }
        private final IRepairStatusEnumService repairStatusEnumService;

        @GetMapping("get/repair_status_enum_list")
        @ApiOperation("查询下拉框的外键信息")
        public BaseResponse get_repair_status_enum_list() {
            List<RepairStatusEnum> list = this.repairStatusEnumService.list();
            final List<SelectVO> selectVoList = list.stream().map(p ->
                new SelectVO(p.getRepairStatusEnumId(), p.getStatusName().toString()))
                        .collect(Collectors.toList());
            return ResultUtils.success(selectVoList);
        }


        /**
         * 导出excel
         */
        @PostMapping("/export")
        public void export(HttpServletResponse response, @RequestBody RepairOrderAllPageVO vo) {

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
                    if(vo.getRoleEnumRoleEnumId1() != null){
                        queryWrapper.eq(RepairOrderAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                    }
                if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
                        queryWrapper.eq(RepairOrderAll::getRoomNumber, vo.getRoomNumber());
                    }
                if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
                        queryWrapper.eq(RepairOrderAll::getQrCodePath, vo.getQrCodePath());
                    }
                if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                        queryWrapper.eq(RepairOrderAll::getPhoneNumber, vo.getPhoneNumber());
                    }
                if(StringUtils.isNotEmpty(vo.getStatusName())) {
                                queryWrapper.like(RepairOrderAll::getStatusName, vo.getStatusName());
                    }
                    if(vo.getRegistrationDate() != null){
                        queryWrapper.eq(RepairOrderAll::getRegistrationDate, vo.getRegistrationDate());
                    }
                if(StringUtils.isNotEmpty(vo.getContent())) {
                        queryWrapper.eq(RepairOrderAll::getContent, vo.getContent());
                    }
                    if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                        queryWrapper.eq(RepairOrderAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                    }
                if(StringUtils.isNotEmpty(vo.getFeedback())) {
                        queryWrapper.eq(RepairOrderAll::getFeedback, vo.getFeedback());
                    }
                if(StringUtils.isNotEmpty(vo.getStudentId())) {
                        queryWrapper.eq(RepairOrderAll::getStudentId, vo.getStudentId());
                    }
                if(StringUtils.isNotEmpty(vo.getPassword())) {
                        queryWrapper.eq(RepairOrderAll::getPassword, vo.getPassword());
                    }
                if(StringUtils.isNotEmpty(vo.getMajor())) {
                        queryWrapper.eq(RepairOrderAll::getMajor, vo.getMajor());
                    }
                    if(vo.getBuildingEnumBuildingEnumId1() != null){
                        queryWrapper.eq(RepairOrderAll::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
                    }
                if(StringUtils.isNotEmpty(vo.getImageFilesResourceKey())) {
                        queryWrapper.eq(RepairOrderAll::getImageFilesResourceKey, vo.getImageFilesResourceKey());
                    }
                if(StringUtils.isNotEmpty(vo.getName())) {
                                queryWrapper.like(RepairOrderAll::getName, vo.getName());
                    }
                    if(vo.getRepairStatusEnumRepairStatusEnumId1() != null){
                        queryWrapper.eq(RepairOrderAll::getRepairStatusEnumRepairStatusEnumId1, vo.getRepairStatusEnumRepairStatusEnumId1());
                    }
                    if(vo.getClassroomInfoClassroomInfoId1() != null){
                        queryWrapper.eq(RepairOrderAll::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
                    }
            List<RepairOrderAll> list = repairOrderAllService.list(queryWrapper);
            ExcelUtil<RepairOrderAll> util = new ExcelUtil<>(RepairOrderAll.class);
            util.exportExcel(response, list, "数据");
        }


}

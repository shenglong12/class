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
import com.kuafu.web.entity.BackgroundImageAll;
import com.kuafu.web.service.IBackgroundImageAllService;
import com.kuafu.web.vo.BackgroundImageAllPageVO;

import com.kuafu.web.service.IUserInfoService;
import com.kuafu.web.entity.UserInfo;


/**
 * <p> 背景图片 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/backgroundImageAll")
@Api(tags = {"背景图片"})
public class BackgroundImageAllController  {

    private final IBackgroundImageAllService backgroundImageAllService;

    @PostMapping("page")
    @ApiOperation("全字段分页")
    public BaseResponse page(@RequestBody BackgroundImageAllPageVO pageVO){
        IPage<BackgroundImageAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<BackgroundImageAll> queryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
            queryWrapper.eq(BackgroundImageAll::getCollege, pageVO.getCollege());
        }
            if(pageVO.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(BackgroundImageAll::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
        }
            if(pageVO.getRoleEnumRoleEnumId1() != null){
            queryWrapper.eq(BackgroundImageAll::getRoleEnumRoleEnumId1, pageVO.getRoleEnumRoleEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
            queryWrapper.eq(BackgroundImageAll::getPhoneNumber, pageVO.getPhoneNumber());
        }
            if(pageVO.getRegistrationDate() != null){
            queryWrapper.eq(BackgroundImageAll::getRegistrationDate, pageVO.getRegistrationDate());
        }
            if(pageVO.getUserStatusEnumUserStatusEnumId1() != null){
            queryWrapper.eq(BackgroundImageAll::getUserStatusEnumUserStatusEnumId1, pageVO.getUserStatusEnumUserStatusEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getStudentId())) {
            queryWrapper.eq(BackgroundImageAll::getStudentId, pageVO.getStudentId());
        }
        if(StringUtils.isNotEmpty(pageVO.getPassword())) {
            queryWrapper.eq(BackgroundImageAll::getPassword, pageVO.getPassword());
        }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
            queryWrapper.eq(BackgroundImageAll::getMajor, pageVO.getMajor());
        }
        if(StringUtils.isNotEmpty(pageVO.getImageFilesResourceKey())) {
            queryWrapper.eq(BackgroundImageAll::getImageFilesResourceKey, pageVO.getImageFilesResourceKey());
        }
        if(StringUtils.isNotEmpty(pageVO.getName())) {
            queryWrapper.like(BackgroundImageAll::getName, pageVO.getName());
        }
            if(pageVO.getUploadTime() != null){
            queryWrapper.eq(BackgroundImageAll::getUploadTime, pageVO.getUploadTime());
        }
        return ResultUtils.success(backgroundImageAllService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("全字段列表")
    public BaseResponse list(@RequestBody BackgroundImageAllPageVO vo){
        LambdaQueryWrapper<BackgroundImageAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCollege())) {
            queryWrapper.eq(BackgroundImageAll::getCollege, vo.getCollege());
        }
                if(vo.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(BackgroundImageAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
        }
                if(vo.getRoleEnumRoleEnumId1() != null){
            queryWrapper.eq(BackgroundImageAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
            queryWrapper.eq(BackgroundImageAll::getPhoneNumber, vo.getPhoneNumber());
        }
                if(vo.getRegistrationDate() != null){
            queryWrapper.eq(BackgroundImageAll::getRegistrationDate, vo.getRegistrationDate());
        }
                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
            queryWrapper.eq(BackgroundImageAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getStudentId())) {
            queryWrapper.eq(BackgroundImageAll::getStudentId, vo.getStudentId());
        }
            if(StringUtils.isNotEmpty(vo.getPassword())) {
            queryWrapper.eq(BackgroundImageAll::getPassword, vo.getPassword());
        }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
            queryWrapper.eq(BackgroundImageAll::getMajor, vo.getMajor());
        }
            if(StringUtils.isNotEmpty(vo.getImageFilesResourceKey())) {
            queryWrapper.eq(BackgroundImageAll::getImageFilesResourceKey, vo.getImageFilesResourceKey());
        }
            if(StringUtils.isNotEmpty(vo.getName())) {
            queryWrapper.like(BackgroundImageAll::getName, vo.getName());
        }
                if(vo.getUploadTime() != null){
            queryWrapper.eq(BackgroundImageAll::getUploadTime, vo.getUploadTime());
        }
        return ResultUtils.success(backgroundImageAllService.list(queryWrapper));
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer backgroundImageId) {
        BackgroundImageAll entity = this.backgroundImageAllService.getById(backgroundImageId);
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
        public void export(HttpServletResponse response, @RequestBody BackgroundImageAllPageVO vo) {

            LambdaQueryWrapper<BackgroundImageAll> queryWrapper = new LambdaQueryWrapper<>();

                if(StringUtils.isNotEmpty(vo.getCollege())) {
                        queryWrapper.eq(BackgroundImageAll::getCollege, vo.getCollege());
                    }
                    if(vo.getUserInfoUserInfoId1() != null){
                        queryWrapper.eq(BackgroundImageAll::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                    }
                    if(vo.getRoleEnumRoleEnumId1() != null){
                        queryWrapper.eq(BackgroundImageAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                    }
                if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                        queryWrapper.eq(BackgroundImageAll::getPhoneNumber, vo.getPhoneNumber());
                    }
                    if(vo.getRegistrationDate() != null){
                        queryWrapper.eq(BackgroundImageAll::getRegistrationDate, vo.getRegistrationDate());
                    }
                    if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                        queryWrapper.eq(BackgroundImageAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                    }
                if(StringUtils.isNotEmpty(vo.getStudentId())) {
                        queryWrapper.eq(BackgroundImageAll::getStudentId, vo.getStudentId());
                    }
                if(StringUtils.isNotEmpty(vo.getPassword())) {
                        queryWrapper.eq(BackgroundImageAll::getPassword, vo.getPassword());
                    }
                if(StringUtils.isNotEmpty(vo.getMajor())) {
                        queryWrapper.eq(BackgroundImageAll::getMajor, vo.getMajor());
                    }
                if(StringUtils.isNotEmpty(vo.getImageFilesResourceKey())) {
                        queryWrapper.eq(BackgroundImageAll::getImageFilesResourceKey, vo.getImageFilesResourceKey());
                    }
                if(StringUtils.isNotEmpty(vo.getName())) {
                                queryWrapper.like(BackgroundImageAll::getName, vo.getName());
                    }
                    if(vo.getUploadTime() != null){
                        queryWrapper.eq(BackgroundImageAll::getUploadTime, vo.getUploadTime());
                    }
            List<BackgroundImageAll> list = backgroundImageAllService.list(queryWrapper);
            ExcelUtil<BackgroundImageAll> util = new ExcelUtil<>(BackgroundImageAll.class);
            util.exportExcel(response, list, "数据");
        }


}

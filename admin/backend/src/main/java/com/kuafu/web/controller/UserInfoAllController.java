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
import com.kuafu.web.entity.UserInfoAll;
import com.kuafu.web.service.IUserInfoAllService;
import com.kuafu.web.vo.UserInfoAllPageVO;

import com.kuafu.web.service.IRoleEnumService;
import com.kuafu.web.entity.RoleEnum;
import com.kuafu.web.service.IUserStatusEnumService;
import com.kuafu.web.entity.UserStatusEnum;


/**
 * <p> 用户信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/userInfoAll")
@Api(tags = {"用户信息"})
public class UserInfoAllController  {

    private final IUserInfoAllService userInfoAllService;

    @PostMapping("page")
    @ApiOperation("全字段分页")
    public BaseResponse page(@RequestBody UserInfoAllPageVO pageVO){
        IPage<UserInfoAll> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<UserInfoAll> queryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotEmpty(pageVO.getStudentId())) {
            queryWrapper.eq(UserInfoAll::getStudentId, pageVO.getStudentId());
        }
        if(StringUtils.isNotEmpty(pageVO.getCollege())) {
            queryWrapper.eq(UserInfoAll::getCollege, pageVO.getCollege());
        }
        if(StringUtils.isNotEmpty(pageVO.getPassword())) {
            queryWrapper.eq(UserInfoAll::getPassword, pageVO.getPassword());
        }
        if(StringUtils.isNotEmpty(pageVO.getMajor())) {
            queryWrapper.eq(UserInfoAll::getMajor, pageVO.getMajor());
        }
            if(pageVO.getRoleEnumRoleEnumId1() != null){
            queryWrapper.eq(UserInfoAll::getRoleEnumRoleEnumId1, pageVO.getRoleEnumRoleEnumId1());
        }
        if(StringUtils.isNotEmpty(pageVO.getRoleName())) {
            queryWrapper.like(UserInfoAll::getRoleName, pageVO.getRoleName());
        }
        if(StringUtils.isNotEmpty(pageVO.getName())) {
            queryWrapper.like(UserInfoAll::getName, pageVO.getName());
        }
        if(StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
            queryWrapper.eq(UserInfoAll::getPhoneNumber, pageVO.getPhoneNumber());
        }
        if(StringUtils.isNotEmpty(pageVO.getStatusName())) {
            queryWrapper.like(UserInfoAll::getStatusName, pageVO.getStatusName());
        }
            if(pageVO.getRegistrationDate() != null){
            queryWrapper.eq(UserInfoAll::getRegistrationDate, pageVO.getRegistrationDate());
        }
            if(pageVO.getUserStatusEnumUserStatusEnumId1() != null){
            queryWrapper.eq(UserInfoAll::getUserStatusEnumUserStatusEnumId1, pageVO.getUserStatusEnumUserStatusEnumId1());
        }
        return ResultUtils.success(userInfoAllService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("全字段列表")
    public BaseResponse list(@RequestBody UserInfoAllPageVO vo){
        LambdaQueryWrapper<UserInfoAll> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getStudentId())) {
            queryWrapper.eq(UserInfoAll::getStudentId, vo.getStudentId());
        }
            if(StringUtils.isNotEmpty(vo.getCollege())) {
            queryWrapper.eq(UserInfoAll::getCollege, vo.getCollege());
        }
            if(StringUtils.isNotEmpty(vo.getPassword())) {
            queryWrapper.eq(UserInfoAll::getPassword, vo.getPassword());
        }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
            queryWrapper.eq(UserInfoAll::getMajor, vo.getMajor());
        }
                if(vo.getRoleEnumRoleEnumId1() != null){
            queryWrapper.eq(UserInfoAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getRoleName())) {
            queryWrapper.like(UserInfoAll::getRoleName, vo.getRoleName());
        }
            if(StringUtils.isNotEmpty(vo.getName())) {
            queryWrapper.like(UserInfoAll::getName, vo.getName());
        }
            if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
            queryWrapper.eq(UserInfoAll::getPhoneNumber, vo.getPhoneNumber());
        }
            if(StringUtils.isNotEmpty(vo.getStatusName())) {
            queryWrapper.like(UserInfoAll::getStatusName, vo.getStatusName());
        }
                if(vo.getRegistrationDate() != null){
            queryWrapper.eq(UserInfoAll::getRegistrationDate, vo.getRegistrationDate());
        }
                if(vo.getUserStatusEnumUserStatusEnumId1() != null){
            queryWrapper.eq(UserInfoAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
        }
        return ResultUtils.success(userInfoAllService.list(queryWrapper));
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer userInfoId) {
        UserInfoAll entity = this.userInfoAllService.getById(userInfoId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


        private final IRoleEnumService roleEnumService;

        @GetMapping("get/role_enum_list")
        @ApiOperation("查询下拉框的外键信息")
        public BaseResponse get_role_enum_list() {
            List<RoleEnum> list = this.roleEnumService.list();
            final List<SelectVO> selectVoList = list.stream().map(p ->
                new SelectVO(p.getRoleEnumId(), p.getRoleName().toString()))
                        .collect(Collectors.toList());
            return ResultUtils.success(selectVoList);
        }
        private final IUserStatusEnumService userStatusEnumService;

        @GetMapping("get/user_status_enum_list")
        @ApiOperation("查询下拉框的外键信息")
        public BaseResponse get_user_status_enum_list() {
            List<UserStatusEnum> list = this.userStatusEnumService.list();
            final List<SelectVO> selectVoList = list.stream().map(p ->
                new SelectVO(p.getUserStatusEnumId(), p.getStatusName().toString()))
                        .collect(Collectors.toList());
            return ResultUtils.success(selectVoList);
        }


        /**
         * 导出excel
         */
        @PostMapping("/export")
        public void export(HttpServletResponse response, @RequestBody UserInfoAllPageVO vo) {

            LambdaQueryWrapper<UserInfoAll> queryWrapper = new LambdaQueryWrapper<>();

                if(StringUtils.isNotEmpty(vo.getStudentId())) {
                        queryWrapper.eq(UserInfoAll::getStudentId, vo.getStudentId());
                    }
                if(StringUtils.isNotEmpty(vo.getCollege())) {
                        queryWrapper.eq(UserInfoAll::getCollege, vo.getCollege());
                    }
                if(StringUtils.isNotEmpty(vo.getPassword())) {
                        queryWrapper.eq(UserInfoAll::getPassword, vo.getPassword());
                    }
                if(StringUtils.isNotEmpty(vo.getMajor())) {
                        queryWrapper.eq(UserInfoAll::getMajor, vo.getMajor());
                    }
                    if(vo.getRoleEnumRoleEnumId1() != null){
                        queryWrapper.eq(UserInfoAll::getRoleEnumRoleEnumId1, vo.getRoleEnumRoleEnumId1());
                    }
                if(StringUtils.isNotEmpty(vo.getRoleName())) {
                                queryWrapper.like(UserInfoAll::getRoleName, vo.getRoleName());
                    }
                if(StringUtils.isNotEmpty(vo.getName())) {
                                queryWrapper.like(UserInfoAll::getName, vo.getName());
                    }
                if(StringUtils.isNotEmpty(vo.getPhoneNumber())) {
                        queryWrapper.eq(UserInfoAll::getPhoneNumber, vo.getPhoneNumber());
                    }
                if(StringUtils.isNotEmpty(vo.getStatusName())) {
                                queryWrapper.like(UserInfoAll::getStatusName, vo.getStatusName());
                    }
                    if(vo.getRegistrationDate() != null){
                        queryWrapper.eq(UserInfoAll::getRegistrationDate, vo.getRegistrationDate());
                    }
                    if(vo.getUserStatusEnumUserStatusEnumId1() != null){
                        queryWrapper.eq(UserInfoAll::getUserStatusEnumUserStatusEnumId1, vo.getUserStatusEnumUserStatusEnumId1());
                    }
            List<UserInfoAll> list = userInfoAllService.list(queryWrapper);
            ExcelUtil<UserInfoAll> util = new ExcelUtil<>(UserInfoAll.class);
            util.exportExcel(response, list, "数据");
        }


}

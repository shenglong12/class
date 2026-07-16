package com.kuafu.login.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.login.SecurityUtils;
import com.kuafu.common.util.SpringUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.login.domain.Login;
import com.kuafu.login.domain.LoginPageVO;
import com.kuafu.login.domain.LoginReverence;
import com.kuafu.login.domain.LoginVO;
import com.kuafu.login.service.ILoginService;
import com.kuafu.login.domain.SelectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 登陆表 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2024/08/18 14:22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/login_manger")
@Api(tags = {"登陆表"})
public class LoginMangerController {

    private final ILoginService loginService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page(@RequestBody LoginPageVO pageVO) {
        IPage<Login> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<Login> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotEmpty(pageVO.getWxOpenId())) {
            queryWrapper.eq(Login::getWxOpenId, pageVO.getWxOpenId());
        }
        if (StringUtils.isNotEmpty(pageVO.getPhoneNumber())) {
            queryWrapper.eq(Login::getPhoneNumber, pageVO.getPhoneNumber());
        }
        if (StringUtils.isNotEmpty(pageVO.getUserName())) {
            queryWrapper.like(Login::getUserName, pageVO.getUserName());
        }
        if (StringUtils.isNotEmpty(pageVO.getRelevanceId())) {
            queryWrapper.eq(Login::getRelevanceId, pageVO.getRelevanceId());
        }
        if (StringUtils.isNotEmpty(pageVO.getRelevanceTable())) {
            queryWrapper.eq(Login::getRelevanceTable, pageVO.getRelevanceTable());
        }
        return ResultUtils.success(loginService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list(@RequestBody LoginVO vo) {
        LambdaQueryWrapper<Login> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotEmpty(vo.getWxOpenId())) {
            queryWrapper.eq(Login::getWxOpenId, vo.getWxOpenId());
        }
        if (StringUtils.isNotEmpty(vo.getPhoneNumber())) {
            queryWrapper.eq(Login::getPhoneNumber, vo.getPhoneNumber());
        }
        if (StringUtils.isNotEmpty(vo.getUserName())) {
            queryWrapper.like(Login::getUserName, vo.getUserName());
        }
        if (StringUtils.isNotEmpty(vo.getRelevanceId())) {
            queryWrapper.eq(Login::getRelevanceId, vo.getRelevanceId());
        }
        if (StringUtils.isNotEmpty(vo.getRelevanceTable())) {
            queryWrapper.eq(Login::getRelevanceTable, vo.getRelevanceTable());
        }
        return ResultUtils.success(loginService.list(queryWrapper));
    }

    @PostMapping("add")
    @ApiOperation("新增")
    public BaseResponse add(@RequestBody LoginVO vo) {
        final String password = SecurityUtils.encryptPassword("123456");
        Login entity = Login.builder()
                .wxOpenId(vo.getWxOpenId())
                .phoneNumber(vo.getPhoneNumber())
                .userName(vo.getUserName())
                .relevanceId(vo.getRelevanceId())
                .password(password)
                .relevanceTable(vo.getRelevanceTable())

                .build();
        boolean flag = this.loginService.save(entity);
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    public BaseResponse update(@RequestBody LoginVO vo) {
        Login entity = Login.builder()
                .loginId(vo.getLoginId())
                .wxOpenId(vo.getWxOpenId())
                .phoneNumber(vo.getPhoneNumber())
                .userName(vo.getUserName())
                .relevanceId(vo.getRelevanceId())
                .relevanceTable(vo.getRelevanceTable())
                .build();
        boolean flag = this.loginService.updateById(entity);
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value = "id") Integer loginId) {
        Login entity = this.loginService.getById(loginId);
        return entity != null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value = "id") Integer loginId) {
        boolean flag = this.loginService.removeById(loginId);
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("relevance/all")
    @ApiOperation("关联业务类型")
    public BaseResponse relevance_all() {
        final List<SelectVo> all = LoginReverence.all();
        return ResultUtils.success(all
        );
    }


//    @GetMapping("relevance/value")
//    @ApiOperation("关联业务的值")
//    public BaseResponse get_relevance_all(String relevance_table) {
//        IService bean = SpringUtils.getBean(relevance_table);
//        final List list = bean.list();
//        return ResultUtils.success(list);
//    }

}

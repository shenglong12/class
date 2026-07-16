package com.kuafu.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.login.SecurityUtils;
import com.kuafu.common.util.excel.ExcelProvider;
import com.kuafu.common.util.poi.ExcelUtil;
import com.kuafu.web.entity.Login;
import com.kuafu.web.entity.SelectVO;
import com.kuafu.web.service.ILoginService;
import com.kuafu.web.vo.LoginPageVO;
import com.kuafu.web.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/login_manger")
@ConditionalOnProperty(prefix = "login", name = "enable")
@Api(value = "LoginMangerController", tags = {"登陆管理"})
@RequiredArgsConstructor
public class LoginMangerController {
    private final ILoginService loginService;

    private final ExcelProvider excelProvider;

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
        final List<SelectVO> all = LoginReverence.all();
        return ResultUtils.success(all);
    }

    @GetMapping("relevance/get_value")
    @ApiOperation("关联业务类型")
    public BaseResponse get_relevance_value(@RequestParam(value = "relevance") String relevance) {
        String value = LoginReverenceValue.getValue(relevance);
        if (StringUtils.isNotEmpty(value)) {
            return ResultUtils.success(value);
        } else {
            return ResultUtils.error();
        }
    }


    @PostMapping("update_password")
    @ApiOperation("修改密码")
    public BaseResponse update_password(@RequestBody LoginVO vo) {
        final Integer loginId = vo.getLoginId();
        final String password = vo.getPassword();
        if (loginId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不能为空");
        }

        final String encryptPassword = SecurityUtils.encryptPassword(password);
        final Login entity = Login.builder().loginId(loginId)
                .password(encryptPassword).build();
        boolean flag = this.loginService.updateById(entity);
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, LoginVO vo) {

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
        List<Login> list = loginService.list(queryWrapper);
        ExcelUtil<Login> util = new ExcelUtil<>(Login.class);
        util.exportExcel(response, list, "数据");
    }

    /**
     * 导入excel
     * @param file
     * @return
     */
    @PostMapping("/import")
    public BaseResponse importExcel(@RequestPart(name = "file") MultipartFile file)
    {
        try {
            excelProvider.importData(file, Login.class, loginService::saveBatch);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error("导入失败");
        }
        return ResultUtils.success("导入成功");
    }

    /**
     * 下载 excel 模版
     * @param response
     * @throws IOException
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        Workbook workbook = excelProvider.downloadExcelTemplate(Login.class);
        // 返回文件流
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}

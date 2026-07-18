package com.kuafu.web.controller;

import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;
import com.kuafu.common.util.excel.ExcelProvider;
import org.apache.poi.ss.usermodel.Workbook;
import com.kuafu.common.file.FileUploadUtils;
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
import org.springframework.web.bind.annotation.RequestPart;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.SelectVO;
import com.kuafu.common.util.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.kuafu.common.sensitive.SensitiveFilter;
import com.kuafu.common.domin.StaticResource;
import com.kuafu.common.resource.service.IStaticResourceService;

import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.web.entity.DynamicApiSetting;
import com.kuafu.web.service.IDynamicApiSettingService;
import com.kuafu.web.vo.DynamicApiSettingPageVO;
import com.kuafu.web.vo.DynamicApiSettingVO;
import com.kuafu.web.event.MyEventService;
import com.kuafu.web.event.EventVo;
/**
 * <p> API配置 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dynamicApiSetting")
@Api(tags = {"API配置"})
public class DynamicApiSettingController  {

    private final IDynamicApiSettingService dynamicApiSettingService;

    private final MyEventService myEventService;

    private final ExcelProvider excelProvider;
    private final IStaticResourceService staticResourceService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page( @RequestBody DynamicApiSettingPageVO pageVO){
        IPage<DynamicApiSetting> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<DynamicApiSetting> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(pageVO.getKeyName())) {
            queryWrapper.like(DynamicApiSetting::getKeyName, pageVO.getKeyName());
        }
            if(StringUtils.isNotEmpty(pageVO.getDescription())) {
            queryWrapper.eq(DynamicApiSetting::getDescription, pageVO.getDescription());
        }
            if(StringUtils.isNotEmpty(pageVO.getUrl())) {
            queryWrapper.eq(DynamicApiSetting::getUrl, pageVO.getUrl());
        }
            if(StringUtils.isNotEmpty(pageVO.getToken())) {
            queryWrapper.eq(DynamicApiSetting::getToken, pageVO.getToken());
        }
            if(StringUtils.isNotEmpty(pageVO.getAppId())) {
            queryWrapper.eq(DynamicApiSetting::getAppId, pageVO.getAppId());
        }
            if(StringUtils.isNotEmpty(pageVO.getApiKey())) {
            queryWrapper.eq(DynamicApiSetting::getApiKey, pageVO.getApiKey());
        }
            if(StringUtils.isNotEmpty(pageVO.getApiSecret())) {
            queryWrapper.eq(DynamicApiSetting::getApiSecret, pageVO.getApiSecret());
        }
            if(StringUtils.isNotEmpty(pageVO.getMethod())) {
            queryWrapper.eq(DynamicApiSetting::getMethod, pageVO.getMethod());
        }
            if(StringUtils.isNotEmpty(pageVO.getBodyType())) {
            queryWrapper.eq(DynamicApiSetting::getBodyType, pageVO.getBodyType());
        }
            if(StringUtils.isNotEmpty(pageVO.getBodyTemplate())) {
            queryWrapper.eq(DynamicApiSetting::getBodyTemplate, pageVO.getBodyTemplate());
        }
            if(StringUtils.isNotEmpty(pageVO.getHeader())) {
            queryWrapper.eq(DynamicApiSetting::getHeader, pageVO.getHeader());
        }
            if(StringUtils.isNotEmpty(pageVO.getAuthType())) {
            queryWrapper.eq(DynamicApiSetting::getAuthType, pageVO.getAuthType());
        }
            if(StringUtils.isNotEmpty(pageVO.getProtocol())) {
            queryWrapper.eq(DynamicApiSetting::getProtocol, pageVO.getProtocol());
        }
            if(StringUtils.isNotEmpty(pageVO.getDataPath())) {
            queryWrapper.eq(DynamicApiSetting::getDataPath, pageVO.getDataPath());
        }
            if(StringUtils.isNotEmpty(pageVO.getDataType())) {
            queryWrapper.eq(DynamicApiSetting::getDataType, pageVO.getDataType());
        }
        return ResultUtils.success(dynamicApiSettingService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list( @RequestBody DynamicApiSettingVO vo){
        LambdaQueryWrapper<DynamicApiSetting> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getKeyName())) {
            queryWrapper.like(DynamicApiSetting::getKeyName, vo.getKeyName());
        }
            if(StringUtils.isNotEmpty(vo.getDescription())) {
            queryWrapper.eq(DynamicApiSetting::getDescription, vo.getDescription());
        }
            if(StringUtils.isNotEmpty(vo.getUrl())) {
            queryWrapper.eq(DynamicApiSetting::getUrl, vo.getUrl());
        }
            if(StringUtils.isNotEmpty(vo.getToken())) {
            queryWrapper.eq(DynamicApiSetting::getToken, vo.getToken());
        }
            if(StringUtils.isNotEmpty(vo.getAppId())) {
            queryWrapper.eq(DynamicApiSetting::getAppId, vo.getAppId());
        }
            if(StringUtils.isNotEmpty(vo.getApiKey())) {
            queryWrapper.eq(DynamicApiSetting::getApiKey, vo.getApiKey());
        }
            if(StringUtils.isNotEmpty(vo.getApiSecret())) {
            queryWrapper.eq(DynamicApiSetting::getApiSecret, vo.getApiSecret());
        }
            if(StringUtils.isNotEmpty(vo.getMethod())) {
            queryWrapper.eq(DynamicApiSetting::getMethod, vo.getMethod());
        }
            if(StringUtils.isNotEmpty(vo.getBodyType())) {
            queryWrapper.eq(DynamicApiSetting::getBodyType, vo.getBodyType());
        }
            if(StringUtils.isNotEmpty(vo.getBodyTemplate())) {
            queryWrapper.eq(DynamicApiSetting::getBodyTemplate, vo.getBodyTemplate());
        }
            if(StringUtils.isNotEmpty(vo.getHeader())) {
            queryWrapper.eq(DynamicApiSetting::getHeader, vo.getHeader());
        }
            if(StringUtils.isNotEmpty(vo.getAuthType())) {
            queryWrapper.eq(DynamicApiSetting::getAuthType, vo.getAuthType());
        }
            if(StringUtils.isNotEmpty(vo.getProtocol())) {
            queryWrapper.eq(DynamicApiSetting::getProtocol, vo.getProtocol());
        }
            if(StringUtils.isNotEmpty(vo.getDataPath())) {
            queryWrapper.eq(DynamicApiSetting::getDataPath, vo.getDataPath());
        }
            if(StringUtils.isNotEmpty(vo.getDataType())) {
            queryWrapper.eq(DynamicApiSetting::getDataType, vo.getDataType());
        }
        return ResultUtils.success(dynamicApiSettingService.list(queryWrapper));
    }


            @PostMapping("get_select_list")
            @ApiOperation("获取所有的下拉列表")
            public BaseResponse get_select_list( ){
                LambdaQueryWrapper<DynamicApiSetting> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(DynamicApiSetting::getId);

        List<DynamicApiSetting> list =dynamicApiSettingService.list(queryWrapper);
        final List<SelectVO> selectVOS = list.stream().map(l -> new SelectVO(l.getId(), l.getId().toString())).collect(Collectors.toList());
        return ResultUtils.success(selectVOS);

                }

    @PostMapping("add")
    @ApiOperation("新增")
    @SensitiveFilter
    public BaseResponse add( @RequestBody DynamicApiSettingVO vo) {
        DynamicApiSetting entity =  DynamicApiSetting.builder()
                    .keyName(vo.getKeyName())
                    .description(vo.getDescription())
                    .url(vo.getUrl())
                    .token(vo.getToken())
                    .appId(vo.getAppId())
                    .apiKey(vo.getApiKey())
                    .apiSecret(vo.getApiSecret())
                    .method(vo.getMethod())
                    .bodyType(vo.getBodyType())
                    .bodyTemplate(vo.getBodyTemplate())
                    .header(vo.getHeader())
                    .authType(vo.getAuthType())
                    .protocol(vo.getProtocol())
                    .dataPath(vo.getDataPath())
                    .dataType(vo.getDataType())
                .build();
        boolean flag =this.dynamicApiSettingService.save(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("dynamicApiSetting").data(entity).build());
                }


        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    @SensitiveFilter
    public BaseResponse update( @RequestBody DynamicApiSettingVO vo) {
        DynamicApiSetting entity =  DynamicApiSetting.builder()
                    .id(vo.getId())
                    .keyName(vo.getKeyName())
                    .description(vo.getDescription())
                    .url(vo.getUrl())
                    .token(vo.getToken())
                    .appId(vo.getAppId())
                    .apiKey(vo.getApiKey())
                    .apiSecret(vo.getApiSecret())
                    .method(vo.getMethod())
                    .bodyType(vo.getBodyType())
                    .bodyTemplate(vo.getBodyTemplate())
                    .header(vo.getHeader())
                    .authType(vo.getAuthType())
                    .protocol(vo.getProtocol())
                    .dataPath(vo.getDataPath())
                    .dataType(vo.getDataType())
                .build();
        boolean flag = this.dynamicApiSettingService.updateById(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("update").tableName("dynamicApiSetting").data(entity).build());
                }



        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer id) {
        DynamicApiSetting entity = this.dynamicApiSettingService.getById(id);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value="id") Integer id) {
        boolean flag = this.dynamicApiSettingService.removeById(id);
        if (flag) {
            myEventService.publishEvent(EventVo.builder().model("delete").tableName("dynamicApiSetting").data( DynamicApiSetting.builder().id(id).build() ).build());
        }
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody DynamicApiSettingPageVO pageVO) {

        LambdaQueryWrapper<DynamicApiSetting> queryWrapper = new LambdaQueryWrapper<>();

                    if(StringUtils.isNotEmpty(pageVO.getKeyName())) {
                                    queryWrapper.like(DynamicApiSetting::getKeyName, pageVO.getKeyName());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getDescription())) {
                            queryWrapper.eq(DynamicApiSetting::getDescription, pageVO.getDescription());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getUrl())) {
                            queryWrapper.eq(DynamicApiSetting::getUrl, pageVO.getUrl());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getToken())) {
                            queryWrapper.eq(DynamicApiSetting::getToken, pageVO.getToken());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getAppId())) {
                            queryWrapper.eq(DynamicApiSetting::getAppId, pageVO.getAppId());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getApiKey())) {
                            queryWrapper.eq(DynamicApiSetting::getApiKey, pageVO.getApiKey());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getApiSecret())) {
                            queryWrapper.eq(DynamicApiSetting::getApiSecret, pageVO.getApiSecret());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getMethod())) {
                            queryWrapper.eq(DynamicApiSetting::getMethod, pageVO.getMethod());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getBodyType())) {
                            queryWrapper.eq(DynamicApiSetting::getBodyType, pageVO.getBodyType());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getBodyTemplate())) {
                            queryWrapper.eq(DynamicApiSetting::getBodyTemplate, pageVO.getBodyTemplate());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getHeader())) {
                            queryWrapper.eq(DynamicApiSetting::getHeader, pageVO.getHeader());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getAuthType())) {
                            queryWrapper.eq(DynamicApiSetting::getAuthType, pageVO.getAuthType());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getProtocol())) {
                            queryWrapper.eq(DynamicApiSetting::getProtocol, pageVO.getProtocol());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getDataPath())) {
                            queryWrapper.eq(DynamicApiSetting::getDataPath, pageVO.getDataPath());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getDataType())) {
                            queryWrapper.eq(DynamicApiSetting::getDataType, pageVO.getDataType());
                        }
            List<DynamicApiSetting> list = dynamicApiSettingService.list(queryWrapper);
            ExcelUtil<DynamicApiSetting> util = new ExcelUtil<>(DynamicApiSetting.class);
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
        String extension = FileUploadUtils.getExtension(file);
        if (StringUtils.equalsIgnoreCase(extension, "pdf")) {
            excelProvider.pdfData(file, DynamicApiSetting.class, dynamicApiSettingService::saveBatch);
        }
        else{
            excelProvider.importData(file, DynamicApiSetting.class, dynamicApiSettingService::saveBatch);
        }
            return ResultUtils.success("导入成功");
    }

    /**
     * 下载 excel 模版
     * @param response
     * @throws IOException
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) {
                    try {
                        Workbook workbook = excelProvider.downloadExcelTemplate(DynamicApiSetting.class);
                        // 返回文件流
                        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                        response.setCharacterEncoding("utf-8");
                        workbook.write(response.getOutputStream());
                        workbook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    }


            
    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    public BaseResponse deleteBatch(@RequestBody List<Integer> ids) {
        boolean flag = this.dynamicApiSettingService.removeByIds(ids);
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }
}

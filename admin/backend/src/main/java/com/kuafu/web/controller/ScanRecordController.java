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
import com.kuafu.common.util.SqliteSequenceReset;
import com.kuafu.common.util.StringUtils;
import com.kuafu.web.entity.ScanRecord;
import com.kuafu.web.service.IScanRecordService;
import com.kuafu.web.vo.ScanRecordPageVO;
import com.kuafu.web.vo.ScanRecordVO;
import com.kuafu.web.event.MyEventService;
import com.kuafu.web.event.EventVo;
/**
 * <p> 扫码记录 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/scanRecord")
@Api(tags = {"扫码记录"})
public class ScanRecordController  {

    private final IScanRecordService scanRecordService;

    private final MyEventService myEventService;

    private final ExcelProvider excelProvider;
    private final SqliteSequenceReset sequenceReset;
    private final IStaticResourceService staticResourceService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page( @RequestBody ScanRecordPageVO pageVO){
        IPage<ScanRecord> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<ScanRecord> queryWrapper = new LambdaQueryWrapper<>();

            if(pageVO.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(ScanRecord::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
        }
            if(StringUtils.isNotEmpty(pageVO.getBuildingName())) {
            queryWrapper.like(ScanRecord::getBuildingName, pageVO.getBuildingName());
        }
            if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
            queryWrapper.eq(ScanRecord::getRoomNumber, pageVO.getRoomNumber());
        }
            if(pageVO.getScanTime() != null){
            queryWrapper.eq(ScanRecord::getScanTime, pageVO.getScanTime());
        }
        return ResultUtils.success(scanRecordService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list( @RequestBody ScanRecordVO vo){
        LambdaQueryWrapper<ScanRecord> queryWrapper = new LambdaQueryWrapper<>();

                if(vo.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(ScanRecord::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
        }
            if(StringUtils.isNotEmpty(vo.getBuildingName())) {
            queryWrapper.like(ScanRecord::getBuildingName, vo.getBuildingName());
        }
            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
            queryWrapper.eq(ScanRecord::getRoomNumber, vo.getRoomNumber());
        }
                if(vo.getScanTime() != null){
            queryWrapper.eq(ScanRecord::getScanTime, vo.getScanTime());
        }
        return ResultUtils.success(scanRecordService.list(queryWrapper));
    }


            @PostMapping("get_select_list")
            @ApiOperation("获取所有的下拉列表")
            public BaseResponse get_select_list( ){
                LambdaQueryWrapper<ScanRecord> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(ScanRecord::getScanId);

        List<ScanRecord> list =scanRecordService.list(queryWrapper);
        final List<SelectVO> selectVOS = list.stream().map(l -> new SelectVO(l.getScanId(), l.getScanId().toString())).collect(Collectors.toList());
        return ResultUtils.success(selectVOS);

                }

    @PostMapping("add")
    @ApiOperation("新增")
    @SensitiveFilter
    public BaseResponse add( @RequestBody ScanRecordVO vo) {
        ScanRecord entity =  ScanRecord.builder()
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .buildingName(vo.getBuildingName())
                    .roomNumber(vo.getRoomNumber())
                    .scanTime(vo.getScanTime())
                .build();
        boolean flag =this.scanRecordService.save(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("scanRecord").data(entity).build());
                }


        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    @SensitiveFilter
    public BaseResponse update( @RequestBody ScanRecordVO vo) {
        ScanRecord entity =  ScanRecord.builder()
                    .scanId(vo.getScanId())
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .buildingName(vo.getBuildingName())
                    .roomNumber(vo.getRoomNumber())
                    .scanTime(vo.getScanTime())
                .build();
        boolean flag = this.scanRecordService.updateById(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("update").tableName("scanRecord").data(entity).build());
                }



        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer scanId) {
        ScanRecord entity = this.scanRecordService.getById(scanId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value="id") Integer scanId) {
        boolean flag = this.scanRecordService.removeById(scanId);
        if (flag) {
            myEventService.publishEvent(EventVo.builder().model("delete").tableName("scanRecord").data( ScanRecord.builder().scanId(scanId).build() ).build());
        }
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody ScanRecordPageVO pageVO) {

        LambdaQueryWrapper<ScanRecord> queryWrapper = new LambdaQueryWrapper<>();

                        if(pageVO.getUserInfoUserInfoId1() != null){
                            queryWrapper.eq(ScanRecord::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getBuildingName())) {
                                    queryWrapper.like(ScanRecord::getBuildingName, pageVO.getBuildingName());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
                            queryWrapper.eq(ScanRecord::getRoomNumber, pageVO.getRoomNumber());
                        }
                        if(pageVO.getScanTime() != null){
                            queryWrapper.eq(ScanRecord::getScanTime, pageVO.getScanTime());
                        }
            List<ScanRecord> list = scanRecordService.list(queryWrapper);
            ExcelUtil<ScanRecord> util = new ExcelUtil<>(ScanRecord.class);
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
            excelProvider.pdfData(file, ScanRecord.class, scanRecordService::save);
        }
        else{
            excelProvider.importData(file, ScanRecord.class, scanRecordService::save);
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
                        Workbook workbook = excelProvider.downloadExcelTemplate(ScanRecord.class);
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
        boolean flag = this.scanRecordService.removeByIds(ids);
        sequenceReset.resetIfEmpty("scan_record");
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }
}

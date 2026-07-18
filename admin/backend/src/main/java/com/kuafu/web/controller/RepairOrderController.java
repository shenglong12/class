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
import com.kuafu.web.entity.RepairOrder;
import com.kuafu.web.service.IRepairOrderService;
import com.kuafu.web.vo.RepairOrderPageVO;
import com.kuafu.web.vo.RepairOrderVO;
import com.kuafu.web.event.MyEventService;
import com.kuafu.web.event.EventVo;
/**
 * <p> 报修单 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/repairOrder")
@Api(tags = {"报修单"})
public class RepairOrderController  {

    private final IRepairOrderService repairOrderService;

    private final MyEventService myEventService;

    private final ExcelProvider excelProvider;
    private final IStaticResourceService staticResourceService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page( @RequestBody RepairOrderPageVO pageVO){
        IPage<RepairOrder> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

            if(pageVO.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(RepairOrder::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
        }
            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(RepairOrder::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
        }
            if(StringUtils.isNotEmpty(pageVO.getContent())) {
            queryWrapper.eq(RepairOrder::getContent, pageVO.getContent());
        }
            if(pageVO.getRepairStatusEnumRepairStatusEnumId1() != null){
            queryWrapper.eq(RepairOrder::getRepairStatusEnumRepairStatusEnumId1, pageVO.getRepairStatusEnumRepairStatusEnumId1());
        }
            if(StringUtils.isNotEmpty(pageVO.getFeedback())) {
            queryWrapper.eq(RepairOrder::getFeedback, pageVO.getFeedback());
        }
            if(pageVO.getCreateTime() != null){
            queryWrapper.eq(RepairOrder::getCreateTime, pageVO.getCreateTime());
        }
            if(StringUtils.isNotEmpty(pageVO.getImageFilesResourceKey())) {
            queryWrapper.eq(RepairOrder::getImageFilesResourceKey, pageVO.getImageFilesResourceKey());
        }
        return ResultUtils.success(repairOrderService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list( @RequestBody RepairOrderVO vo){
        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

                if(vo.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(RepairOrder::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
        }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(RepairOrder::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
        }
            if(StringUtils.isNotEmpty(vo.getContent())) {
            queryWrapper.eq(RepairOrder::getContent, vo.getContent());
        }
                if(vo.getRepairStatusEnumRepairStatusEnumId1() != null){
            queryWrapper.eq(RepairOrder::getRepairStatusEnumRepairStatusEnumId1, vo.getRepairStatusEnumRepairStatusEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getFeedback())) {
            queryWrapper.eq(RepairOrder::getFeedback, vo.getFeedback());
        }
                if(vo.getCreateTime() != null){
            queryWrapper.eq(RepairOrder::getCreateTime, vo.getCreateTime());
        }
            if(StringUtils.isNotEmpty(vo.getImageFilesResourceKey())) {
            queryWrapper.eq(RepairOrder::getImageFilesResourceKey, vo.getImageFilesResourceKey());
        }
        return ResultUtils.success(repairOrderService.list(queryWrapper));
    }


            @PostMapping("get_select_list")
            @ApiOperation("获取所有的下拉列表")
            public BaseResponse get_select_list( ){
                LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(RepairOrder::getRepairOrderId);

        List<RepairOrder> list =repairOrderService.list(queryWrapper);
        final List<SelectVO> selectVOS = list.stream().map(l -> new SelectVO(l.getRepairOrderId(), l.getRepairOrderId().toString())).collect(Collectors.toList());
        return ResultUtils.success(selectVOS);

                }

    @PostMapping("add")
    @ApiOperation("新增")
    @SensitiveFilter
    public BaseResponse add( @RequestBody RepairOrderVO vo) {
        RepairOrder entity =  RepairOrder.builder()
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .content(vo.getContent())
                    .repairStatusEnumRepairStatusEnumId1(vo.getRepairStatusEnumRepairStatusEnumId1())
                    .feedback(vo.getFeedback())
                  .imageFilesResourceKey(vo.getImageFilesResourceKey() == null ? "default_resource_key" : vo.getImageFilesResourceKey())
                .build();
        boolean flag =this.repairOrderService.save(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("repairOrder").data(entity).build());
                }

                final List<StaticResource> ImageFilesImage = vo.getImageFiles();
                if (ImageFilesImage  != null && !ImageFilesImage.isEmpty()) {
                    ImageFilesImage.forEach(i -> {
                        i.setRelateTableColumnName("image_files");
                        i.setRelatedTableName("repair_order");
                        i.setRelatedTableKey(entity.getRepairOrderId());
                        // 保留原始的resourcePath，确保路径正确
                    });

                    staticResourceService.saveBatch(ImageFilesImage);

                }

        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    @SensitiveFilter
    public BaseResponse update( @RequestBody RepairOrderVO vo) {
        RepairOrder entity =  RepairOrder.builder()
                    .repairOrderId(vo.getRepairOrderId())
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .content(vo.getContent())
                    .repairStatusEnumRepairStatusEnumId1(vo.getRepairStatusEnumRepairStatusEnumId1())
                    .feedback(vo.getFeedback())
                    .imageFilesResourceKey(vo.getImageFilesResourceKey())
                .build();
        boolean flag = this.repairOrderService.updateById(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("update").tableName("repairOrder").data(entity).build());
                }

                final LambdaQueryWrapper<StaticResource> image_filesqueryWrapper = new LambdaQueryWrapper<>();
                image_filesqueryWrapper
                        .eq(StaticResource::getRelatedTableName,"repair_order")
                        .eq(StaticResource::getRelateTableColumnName,"image_files")
                        .eq(StaticResource::getRelatedTableKey,entity.getRepairOrderId());
                staticResourceService.remove(image_filesqueryWrapper);


                final List<StaticResource> ImageFilesImage = vo.getImageFiles();
                if (ImageFilesImage  != null && !ImageFilesImage.isEmpty()) {
                    ImageFilesImage.forEach(i -> {
                        i.setRelateTableColumnName("image_files");
                        i.setRelatedTableName("repair_order");
                        i.setRelatedTableKey(entity.getRepairOrderId());
                        // 保留原始的resourcePath，确保路径正确
                    });

                    staticResourceService.saveBatch(ImageFilesImage);

                }



        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer repairOrderId) {
        RepairOrder entity = this.repairOrderService.getById(repairOrderId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value="id") Integer repairOrderId) {
        boolean flag = this.repairOrderService.removeById(repairOrderId);
        if (flag) {
            myEventService.publishEvent(EventVo.builder().model("delete").tableName("repairOrder").data( RepairOrder.builder().repairOrderId(repairOrderId).build() ).build());
        }
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody RepairOrderPageVO pageVO) {

        LambdaQueryWrapper<RepairOrder> queryWrapper = new LambdaQueryWrapper<>();

                        if(pageVO.getUserInfoUserInfoId1() != null){
                            queryWrapper.eq(RepairOrder::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
                        }
                        if(pageVO.getClassroomInfoClassroomInfoId1() != null){
                            queryWrapper.eq(RepairOrder::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getContent())) {
                            queryWrapper.eq(RepairOrder::getContent, pageVO.getContent());
                        }
                        if(pageVO.getRepairStatusEnumRepairStatusEnumId1() != null){
                            queryWrapper.eq(RepairOrder::getRepairStatusEnumRepairStatusEnumId1, pageVO.getRepairStatusEnumRepairStatusEnumId1());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getFeedback())) {
                            queryWrapper.eq(RepairOrder::getFeedback, pageVO.getFeedback());
                        }
                        if(pageVO.getCreateTime() != null){
                            queryWrapper.eq(RepairOrder::getCreateTime, pageVO.getCreateTime());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getImageFilesResourceKey())) {
                            queryWrapper.eq(RepairOrder::getImageFilesResourceKey, pageVO.getImageFilesResourceKey());
                        }
            List<RepairOrder> list = repairOrderService.list(queryWrapper);
            ExcelUtil<RepairOrder> util = new ExcelUtil<>(RepairOrder.class);
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
            excelProvider.pdfData(file, RepairOrder.class, repairOrderService::saveBatch);
        }
        else{
            excelProvider.importData(file, RepairOrder.class, repairOrderService::saveBatch);
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
                        Workbook workbook = excelProvider.downloadExcelTemplate(RepairOrder.class);
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
        boolean flag = this.repairOrderService.removeByIds(ids);
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }
}

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
import com.kuafu.web.entity.RepairStatusEnum;
import com.kuafu.web.service.IRepairStatusEnumService;
import com.kuafu.web.vo.RepairStatusEnumPageVO;
import com.kuafu.web.vo.RepairStatusEnumVO;
import com.kuafu.web.event.MyEventService;
import com.kuafu.web.event.EventVo;
/**
 * <p> 报修状态 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/repairStatusEnum")
@Api(tags = {"报修状态"})
public class RepairStatusEnumController  {

    private final IRepairStatusEnumService repairStatusEnumService;

    private final MyEventService myEventService;

    private final ExcelProvider excelProvider;
    private final SqliteSequenceReset sequenceReset;
    private final IStaticResourceService staticResourceService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page( @RequestBody RepairStatusEnumPageVO pageVO){
        IPage<RepairStatusEnum> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(pageVO.getStatusName())) {
            queryWrapper.like(RepairStatusEnum::getStatusName, pageVO.getStatusName());
        }
        return ResultUtils.success(repairStatusEnumService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list( @RequestBody RepairStatusEnumVO vo){
        LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getStatusName())) {
            queryWrapper.like(RepairStatusEnum::getStatusName, vo.getStatusName());
        }
        return ResultUtils.success(repairStatusEnumService.list(queryWrapper));
    }


            @PostMapping("get_select_list")
            @ApiOperation("获取所有的下拉列表")
            public BaseResponse get_select_list( ){
                LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(RepairStatusEnum::getRepairStatusEnumId);

        List<RepairStatusEnum> list =repairStatusEnumService.list(queryWrapper);
        final List<SelectVO> selectVOS = list.stream().map(l -> new SelectVO(l.getRepairStatusEnumId(), l.getRepairStatusEnumId().toString())).collect(Collectors.toList());
        return ResultUtils.success(selectVOS);

                }

    @PostMapping("add")
    @ApiOperation("新增")
    @SensitiveFilter
    public BaseResponse add( @RequestBody RepairStatusEnumVO vo) {
        RepairStatusEnum entity =  RepairStatusEnum.builder()
                    .statusName(vo.getStatusName())
                .build();
        boolean flag =this.repairStatusEnumService.save(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("repairStatusEnum").data(entity).build());
                }


        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    @SensitiveFilter
    public BaseResponse update( @RequestBody RepairStatusEnumVO vo) {
        RepairStatusEnum entity =  RepairStatusEnum.builder()
                    .repairStatusEnumId(vo.getRepairStatusEnumId())
                    .statusName(vo.getStatusName())
                .build();
        boolean flag = this.repairStatusEnumService.updateById(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("update").tableName("repairStatusEnum").data(entity).build());
                }



        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer repairStatusEnumId) {
        RepairStatusEnum entity = this.repairStatusEnumService.getById(repairStatusEnumId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value="id") Integer repairStatusEnumId) {
        boolean flag = this.repairStatusEnumService.removeById(repairStatusEnumId);
        if (flag) {
            myEventService.publishEvent(EventVo.builder().model("delete").tableName("repairStatusEnum").data( RepairStatusEnum.builder().repairStatusEnumId(repairStatusEnumId).build() ).build());
        }
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody RepairStatusEnumPageVO pageVO) {

        LambdaQueryWrapper<RepairStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

                    if(StringUtils.isNotEmpty(pageVO.getStatusName())) {
                                    queryWrapper.like(RepairStatusEnum::getStatusName, pageVO.getStatusName());
                        }
            List<RepairStatusEnum> list = repairStatusEnumService.list(queryWrapper);
            ExcelUtil<RepairStatusEnum> util = new ExcelUtil<>(RepairStatusEnum.class);
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
            excelProvider.pdfData(file, RepairStatusEnum.class, entity -> {
            LambdaQueryWrapper<RepairStatusEnum> qw = new LambdaQueryWrapper<>();
            qw.eq(RepairStatusEnum::getStatusName, entity.getStatusName());
            if (repairStatusEnumService.count(qw) == 0) {
                repairStatusEnumService.save(entity);
            }
        });
        }
        else{
            excelProvider.importData(file, RepairStatusEnum.class, entity -> {
            LambdaQueryWrapper<RepairStatusEnum> qw = new LambdaQueryWrapper<>();
            qw.eq(RepairStatusEnum::getStatusName, entity.getStatusName());
            if (repairStatusEnumService.count(qw) == 0) {
                repairStatusEnumService.save(entity);
            }
        });
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
                        Workbook workbook = excelProvider.downloadExcelTemplate(RepairStatusEnum.class);
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
        boolean flag = this.repairStatusEnumService.removeByIds(ids);
        sequenceReset.resetIfEmpty("repair_status_enum");
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }
}

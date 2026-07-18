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
import com.kuafu.web.entity.BuildingEnum;
import com.kuafu.web.service.IBuildingEnumService;
import com.kuafu.web.vo.BuildingEnumPageVO;
import com.kuafu.web.vo.BuildingEnumVO;
import com.kuafu.web.event.MyEventService;
import com.kuafu.web.event.EventVo;
/**
 * <p> 教学楼枚举 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/buildingEnum")
@Api(tags = {"教学楼枚举"})
public class BuildingEnumController  {

    private final IBuildingEnumService buildingEnumService;

    private final MyEventService myEventService;

    private final ExcelProvider excelProvider;
    private final SqliteSequenceReset sequenceReset;
    private final IStaticResourceService staticResourceService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page( @RequestBody BuildingEnumPageVO pageVO){
        IPage<BuildingEnum> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<BuildingEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(pageVO.getBuildingName())) {
            queryWrapper.like(BuildingEnum::getBuildingName, pageVO.getBuildingName());
        }
        return ResultUtils.success(buildingEnumService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list( @RequestBody BuildingEnumVO vo){
        LambdaQueryWrapper<BuildingEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getBuildingName())) {
            queryWrapper.like(BuildingEnum::getBuildingName, vo.getBuildingName());
        }
        return ResultUtils.success(buildingEnumService.list(queryWrapper));
    }


            @PostMapping("get_select_list")
            @ApiOperation("获取所有的下拉列表")
            public BaseResponse get_select_list( ){
                LambdaQueryWrapper<BuildingEnum> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(BuildingEnum::getBuildingEnumId);

        List<BuildingEnum> list =buildingEnumService.list(queryWrapper);
        final List<SelectVO> selectVOS = list.stream().map(l -> new SelectVO(l.getBuildingEnumId(), l.getBuildingEnumId().toString())).collect(Collectors.toList());
        return ResultUtils.success(selectVOS);

                }

    @PostMapping("add")
    @ApiOperation("新增")
    @SensitiveFilter
    public BaseResponse add( @RequestBody BuildingEnumVO vo) {
        BuildingEnum entity =  BuildingEnum.builder()
                    .buildingName(vo.getBuildingName())
                .build();
        boolean flag =this.buildingEnumService.save(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("buildingEnum").data(entity).build());
                }


        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    @SensitiveFilter
    public BaseResponse update( @RequestBody BuildingEnumVO vo) {
        BuildingEnum entity =  BuildingEnum.builder()
                    .buildingEnumId(vo.getBuildingEnumId())
                    .buildingName(vo.getBuildingName())
                .build();
        boolean flag = this.buildingEnumService.updateById(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("update").tableName("buildingEnum").data(entity).build());
                }



        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer buildingEnumId) {
        BuildingEnum entity = this.buildingEnumService.getById(buildingEnumId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value="id") Integer buildingEnumId) {
        boolean flag = this.buildingEnumService.removeById(buildingEnumId);
        if (flag) {
            myEventService.publishEvent(EventVo.builder().model("delete").tableName("buildingEnum").data( BuildingEnum.builder().buildingEnumId(buildingEnumId).build() ).build());
        }
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody BuildingEnumPageVO pageVO) {

        LambdaQueryWrapper<BuildingEnum> queryWrapper = new LambdaQueryWrapper<>();

                    if(StringUtils.isNotEmpty(pageVO.getBuildingName())) {
                                    queryWrapper.like(BuildingEnum::getBuildingName, pageVO.getBuildingName());
                        }
            List<BuildingEnum> list = buildingEnumService.list(queryWrapper);
            ExcelUtil<BuildingEnum> util = new ExcelUtil<>(BuildingEnum.class);
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
            excelProvider.pdfData(file, BuildingEnum.class, entity -> {
            LambdaQueryWrapper<BuildingEnum> qw = new LambdaQueryWrapper<>();
            qw.eq(BuildingEnum::getBuildingName, entity.getBuildingName());
            if (buildingEnumService.count(qw) == 0) {
                buildingEnumService.save(entity);
            }
        });
        }
        else{
            excelProvider.importData(file, BuildingEnum.class, entity -> {
            LambdaQueryWrapper<BuildingEnum> qw = new LambdaQueryWrapper<>();
            qw.eq(BuildingEnum::getBuildingName, entity.getBuildingName());
            if (buildingEnumService.count(qw) == 0) {
                buildingEnumService.save(entity);
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
                        Workbook workbook = excelProvider.downloadExcelTemplate(BuildingEnum.class);
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
        boolean flag = this.buildingEnumService.removeByIds(ids);
        sequenceReset.resetIfEmpty("building_enum");
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }
}

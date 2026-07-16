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
import com.kuafu.web.entity.ClassroomInfo;
import com.kuafu.web.service.IClassroomInfoService;
import com.kuafu.web.vo.ClassroomInfoPageVO;
import com.kuafu.web.vo.ClassroomInfoVO;
import com.kuafu.web.event.MyEventService;
import com.kuafu.web.event.EventVo;
/**
 * <p> 教室信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/classroomInfo")
@Api(tags = {"教室信息"})
public class ClassroomInfoController  {

    private final IClassroomInfoService classroomInfoService;

    private final MyEventService myEventService;

    private final ExcelProvider excelProvider;
    private final IStaticResourceService staticResourceService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page( @RequestBody ClassroomInfoPageVO pageVO){
        IPage<ClassroomInfo> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();

            if(pageVO.getBuildingEnumBuildingEnumId1() != null){
            queryWrapper.eq(ClassroomInfo::getBuildingEnumBuildingEnumId1, pageVO.getBuildingEnumBuildingEnumId1());
        }
            if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
            queryWrapper.eq(ClassroomInfo::getRoomNumber, pageVO.getRoomNumber());
        }
            if(StringUtils.isNotEmpty(pageVO.getQrCodePath())) {
            queryWrapper.eq(ClassroomInfo::getQrCodePath, pageVO.getQrCodePath());
        }
        return ResultUtils.success(classroomInfoService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list( @RequestBody ClassroomInfoVO vo){
        LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();

                if(vo.getBuildingEnumBuildingEnumId1() != null){
            queryWrapper.eq(ClassroomInfo::getBuildingEnumBuildingEnumId1, vo.getBuildingEnumBuildingEnumId1());
        }
            if(StringUtils.isNotEmpty(vo.getRoomNumber())) {
            queryWrapper.eq(ClassroomInfo::getRoomNumber, vo.getRoomNumber());
        }
            if(StringUtils.isNotEmpty(vo.getQrCodePath())) {
            queryWrapper.eq(ClassroomInfo::getQrCodePath, vo.getQrCodePath());
        }
        return ResultUtils.success(classroomInfoService.list(queryWrapper));
    }


            @PostMapping("get_select_list")
            @ApiOperation("获取所有的下拉列表")
            public BaseResponse get_select_list( ){
                LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(ClassroomInfo::getClassroomInfoId);

        List<ClassroomInfo> list =classroomInfoService.list(queryWrapper);
        final List<SelectVO> selectVOS = list.stream().map(l -> new SelectVO(l.getClassroomInfoId(), l.getClassroomInfoId().toString())).collect(Collectors.toList());
        return ResultUtils.success(selectVOS);

                }

    @PostMapping("add")
    @ApiOperation("新增")
    @SensitiveFilter
    public BaseResponse add( @RequestBody ClassroomInfoVO vo) {
        ClassroomInfo entity =  ClassroomInfo.builder()
                    .buildingEnumBuildingEnumId1(vo.getBuildingEnumBuildingEnumId1())
                    .roomNumber(vo.getRoomNumber())
                    .qrCodePath(vo.getQrCodePath())
                .build();
        boolean flag =this.classroomInfoService.save(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("classroomInfo").data(entity).build());
                }


        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    @SensitiveFilter
    public BaseResponse update( @RequestBody ClassroomInfoVO vo) {
        ClassroomInfo entity =  ClassroomInfo.builder()
                    .classroomInfoId(vo.getClassroomInfoId())
                    .buildingEnumBuildingEnumId1(vo.getBuildingEnumBuildingEnumId1())
                    .roomNumber(vo.getRoomNumber())
                    .qrCodePath(vo.getQrCodePath())
                .build();
        boolean flag = this.classroomInfoService.updateById(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("update").tableName("classroomInfo").data(entity).build());
                }



        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer classroomInfoId) {
        ClassroomInfo entity = this.classroomInfoService.getById(classroomInfoId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value="id") Integer classroomInfoId) {
        boolean flag = this.classroomInfoService.removeById(classroomInfoId);
        if (flag) {
            myEventService.publishEvent(EventVo.builder().model("delete").tableName("classroomInfo").data( ClassroomInfo.builder().classroomInfoId(classroomInfoId).build() ).build());
        }
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody ClassroomInfoPageVO pageVO) {

        LambdaQueryWrapper<ClassroomInfo> queryWrapper = new LambdaQueryWrapper<>();

                        if(pageVO.getBuildingEnumBuildingEnumId1() != null){
                            queryWrapper.eq(ClassroomInfo::getBuildingEnumBuildingEnumId1, pageVO.getBuildingEnumBuildingEnumId1());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
                            queryWrapper.eq(ClassroomInfo::getRoomNumber, pageVO.getRoomNumber());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getQrCodePath())) {
                            queryWrapper.eq(ClassroomInfo::getQrCodePath, pageVO.getQrCodePath());
                        }
            List<ClassroomInfo> list = classroomInfoService.list(queryWrapper);
            ExcelUtil<ClassroomInfo> util = new ExcelUtil<>(ClassroomInfo.class);
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
            excelProvider.pdfData(file, ClassroomInfo.class, classroomInfoService::saveBatch);
        }
        else{
            excelProvider.importData(file, ClassroomInfo.class, classroomInfoService::saveBatch);
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
                        Workbook workbook = excelProvider.downloadExcelTemplate(ClassroomInfo.class);
                        // 返回文件流
                        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                        response.setCharacterEncoding("utf-8");
                        workbook.write(response.getOutputStream());
                        workbook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    }


            }

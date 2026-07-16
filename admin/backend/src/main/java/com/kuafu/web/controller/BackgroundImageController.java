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
import com.kuafu.web.entity.BackgroundImage;
import com.kuafu.web.service.IBackgroundImageService;
import com.kuafu.web.vo.BackgroundImagePageVO;
import com.kuafu.web.vo.BackgroundImageVO;
import com.kuafu.web.event.MyEventService;
import com.kuafu.web.event.EventVo;
/**
 * <p> 背景图片 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/backgroundImage")
@Api(tags = {"背景图片"})
public class BackgroundImageController  {

    private final IBackgroundImageService backgroundImageService;

    private final MyEventService myEventService;

    private final ExcelProvider excelProvider;
    private final IStaticResourceService staticResourceService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page( @RequestBody BackgroundImagePageVO pageVO){
        IPage<BackgroundImage> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();

            if(pageVO.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(BackgroundImage::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
        }
            if(StringUtils.isNotEmpty(pageVO.getImageFilesResourceKey())) {
            queryWrapper.eq(BackgroundImage::getImageFilesResourceKey, pageVO.getImageFilesResourceKey());
        }
            if(pageVO.getUploadTime() != null){
            queryWrapper.eq(BackgroundImage::getUploadTime, pageVO.getUploadTime());
        }
        return ResultUtils.success(backgroundImageService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list( @RequestBody BackgroundImageVO vo){
        LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();

                if(vo.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(BackgroundImage::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
        }
            if(StringUtils.isNotEmpty(vo.getImageFilesResourceKey())) {
            queryWrapper.eq(BackgroundImage::getImageFilesResourceKey, vo.getImageFilesResourceKey());
        }
                if(vo.getUploadTime() != null){
            queryWrapper.eq(BackgroundImage::getUploadTime, vo.getUploadTime());
        }
        return ResultUtils.success(backgroundImageService.list(queryWrapper));
    }


            @PostMapping("get_select_list")
            @ApiOperation("获取所有的下拉列表")
            public BaseResponse get_select_list( ){
                LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(BackgroundImage::getBackgroundImageId);

        List<BackgroundImage> list =backgroundImageService.list(queryWrapper);
        final List<SelectVO> selectVOS = list.stream().map(l -> new SelectVO(l.getBackgroundImageId(), l.getBackgroundImageId().toString())).collect(Collectors.toList());
        return ResultUtils.success(selectVOS);

                }

    @PostMapping("add")
    @ApiOperation("新增")
    @SensitiveFilter
    public BaseResponse add( @RequestBody BackgroundImageVO vo) {
        BackgroundImage entity =  BackgroundImage.builder()
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                  .imageFilesResourceKey(vo.getImageFilesResourceKey() == null ? "default_resource_key" : vo.getImageFilesResourceKey())
                    .uploadTime(vo.getUploadTime())
                .build();
        boolean flag =this.backgroundImageService.save(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("backgroundImage").data(entity).build());
                }

                final List<StaticResource> ImageFilesImage = vo.getImageFiles();
                if (ImageFilesImage  != null && !ImageFilesImage.isEmpty()) {
                    ImageFilesImage.forEach(i -> {
                        i.setRelateTableColumnName("image_files");
                        i.setRelatedTableName("background_image");
                        i.setRelatedTableKey(entity.getBackgroundImageId());
                        i.setResourcePath(i.getResourceName());
                    });

                    staticResourceService.saveBatch(ImageFilesImage);

                }

        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    @SensitiveFilter
    public BaseResponse update( @RequestBody BackgroundImageVO vo) {
        BackgroundImage entity =  BackgroundImage.builder()
                    .backgroundImageId(vo.getBackgroundImageId())
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .imageFilesResourceKey(vo.getImageFilesResourceKey())
                    .uploadTime(vo.getUploadTime())
                .build();
        boolean flag = this.backgroundImageService.updateById(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("update").tableName("backgroundImage").data(entity).build());
                }

                final LambdaQueryWrapper<StaticResource> image_filesqueryWrapper = new LambdaQueryWrapper<>();
                image_filesqueryWrapper
                        .eq(StaticResource::getRelatedTableName,"background_image")
                        .eq(StaticResource::getRelateTableColumnName,"image_files")
                        .eq(StaticResource::getRelatedTableKey,entity.getBackgroundImageId());
                staticResourceService.remove(image_filesqueryWrapper);


                final List<StaticResource> ImageFilesImage = vo.getImageFiles();
                if (ImageFilesImage  != null && !ImageFilesImage.isEmpty()) {
                    ImageFilesImage.forEach(i -> {
                        i.setRelateTableColumnName("image_files");
                        i.setRelatedTableName("background_image");
                        i.setRelatedTableKey(entity.getBackgroundImageId());
                        if(StringUtils.isNotEmpty(i.getResourceName())) {
                            i.setResourcePath(i.getResourceName());
                        }
                    });

                    staticResourceService.saveBatch(ImageFilesImage);

                }



        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer backgroundImageId) {
        BackgroundImage entity = this.backgroundImageService.getById(backgroundImageId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value="id") Integer backgroundImageId) {
        boolean flag = this.backgroundImageService.removeById(backgroundImageId);
        if (flag) {
            myEventService.publishEvent(EventVo.builder().model("delete").tableName("backgroundImage").data( BackgroundImage.builder().backgroundImageId(backgroundImageId).build() ).build());
        }
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody BackgroundImagePageVO pageVO) {

        LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();

                        if(pageVO.getUserInfoUserInfoId1() != null){
                            queryWrapper.eq(BackgroundImage::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getImageFilesResourceKey())) {
                            queryWrapper.eq(BackgroundImage::getImageFilesResourceKey, pageVO.getImageFilesResourceKey());
                        }
                        if(pageVO.getUploadTime() != null){
                            queryWrapper.eq(BackgroundImage::getUploadTime, pageVO.getUploadTime());
                        }
            List<BackgroundImage> list = backgroundImageService.list(queryWrapper);
            ExcelUtil<BackgroundImage> util = new ExcelUtil<>(BackgroundImage.class);
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
            excelProvider.pdfData(file, BackgroundImage.class, backgroundImageService::saveBatch);
        }
        else{
            excelProvider.importData(file, BackgroundImage.class, backgroundImageService::saveBatch);
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
                        Workbook workbook = excelProvider.downloadExcelTemplate(BackgroundImage.class);
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

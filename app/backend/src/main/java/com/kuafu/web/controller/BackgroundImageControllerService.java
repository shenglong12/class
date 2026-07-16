package com.kuafu.web.controller;

import java.util.List;
import com.kuafu.common.entity.StaticResource;
import com.kuafu.common.resource.service.IStaticResourceService;
import com.kuafu.common.event.EventVo;
import com.kuafu.common.event.MyEventService;
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
import org.springframework.transaction.support.TransactionTemplate;
import com.kuafu.common.sensitive.SensitiveFilter;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.util.StringUtils;
import com.kuafu.web.entity.BackgroundImage;
import com.kuafu.web.service.IBackgroundImageService;
import com.kuafu.web.vo.BackgroundImagePageVO;
import com.kuafu.web.vo.BackgroundImageVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 背景图片 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("BackgroundImageControllerService")
@RequiredArgsConstructor
public class BackgroundImageControllerService  {

    private final IBackgroundImageService backgroundImageService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( BackgroundImagePageVO pageVO){
        IPage<BackgroundImage> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<BackgroundImage> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<BackgroundImage> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(BackgroundImage::getBackgroundImageId);

            if(pageVO.getBackgroundImageId() != null){
                queryWrapper.eq(BackgroundImage::getBackgroundImageId, pageVO.getBackgroundImageId());
            }


            if(pageVO.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(BackgroundImage::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
            }


            if(pageVO.getUploadTime() != null){
                queryWrapper.eq(BackgroundImage::getUploadTime, pageVO.getUploadTime());
            }

            return ResultUtils.success(backgroundImageService.page(page, queryWrapper));
        }


        public BaseResponse list(  BackgroundImageVO vo){
            LambdaQueryWrapper<BackgroundImage> queryWrapper = new LambdaQueryWrapper<>();

                if(vo.getUserInfoUserInfoId1() != null){
                    queryWrapper.eq(BackgroundImage::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
                }
                if(vo.getUploadTime() != null){
                    queryWrapper.eq(BackgroundImage::getUploadTime, vo.getUploadTime());
                }
                return ResultUtils.success(backgroundImageService.list(queryWrapper));
            }


            @SensitiveFilter
            public BaseResponse add(  BackgroundImageVO vo) {
                BackgroundImage entity =  BackgroundImage.builder()
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .uploadTime(vo.getUploadTime())
                .build();







                boolean  f = transactionTemplate.execute(status -> {
                    try {

                boolean flag =this.backgroundImageService.save(entity);


                final List<StaticResource> ImageFilesImage = vo.getImageFiles();
                if (ImageFilesImage  != null && !ImageFilesImage .isEmpty()) {
                    ImageFilesImage.forEach(i -> {
                        i.setRelateTableColumnName("image_files");
                        i.setRelatedTableName("background_image");
                        i.setResourceId(null);
                        i.setRelatedTableKey(entity.getBackgroundImageId());
                    });
                }
                staticResourceService.saveBatch(ImageFilesImage);




                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        status.setRollbackOnly();
                        throw e;
                    }
                });


                if(
                f
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("background_image").data(entity).build());
                }

                return
                                f
                              
                ? ResultUtils.success(entity.getBackgroundImageId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( BackgroundImageVO vo) {
                BackgroundImage entity =  BackgroundImage.builder()
                    .backgroundImageId(vo.getBackgroundImageId())
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .uploadTime(vo.getUploadTime())
                .build();


                boolean  f = transactionTemplate.execute(status -> {
                    try {

                        boolean flag = this.backgroundImageService.saveOrUpdate(entity);



                        final List<StaticResource> ImageFilesImage = vo.getImageFiles();

                        if (ImageFilesImage  != null) {
                        final LambdaQueryWrapper<StaticResource> image_filesqueryWrapper = new LambdaQueryWrapper<>();
                        image_filesqueryWrapper.eq(StaticResource::getRelatedTableName,"background_image").eq(StaticResource::getRelateTableColumnName,"image_files").eq(StaticResource::getRelatedTableKey,entity.getBackgroundImageId());
                        staticResourceService.remove(image_filesqueryWrapper);
                        }


                        if (ImageFilesImage  != null && !ImageFilesImage .isEmpty()) {
                            ImageFilesImage.forEach(i -> {
                                i.setRelateTableColumnName("image_files");
                                i.setRelatedTableName("background_image");
                                i.setRelatedTableKey(entity.getBackgroundImageId());
                            });
                        }
                        staticResourceService.saveBatch(ImageFilesImage);




                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        status.setRollbackOnly();
                        throw e;
                    }
                });


                return  
                f
                         ? ResultUtils.success(entity.getBackgroundImageId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( BackgroundImageVO vo ) {
                BackgroundImage entity = this.backgroundImageService.getById(vo.getBackgroundImageId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( BackgroundImageVO vo) {
                boolean flag = this.backgroundImageService.removeById(vo.getBackgroundImageId());




                final LambdaQueryWrapper<StaticResource> image_filesqueryWrapper = new LambdaQueryWrapper<>();
                image_filesqueryWrapper.eq(StaticResource::getRelatedTableName,"background_image").eq(StaticResource::getRelateTableColumnName,"image_files").eq(StaticResource::getRelatedTableKey,vo.getBackgroundImageId());
                staticResourceService.remove(image_filesqueryWrapper);


                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

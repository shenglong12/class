package com.kuafu.web.controller;

import java.util.List;
import java.util.ArrayList;
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
import com.kuafu.web.entity.RepairOrder;
import com.kuafu.web.service.IRepairOrderService;
import com.kuafu.web.vo.RepairOrderPageVO;
import com.kuafu.web.vo.RepairOrderVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 报修单 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("RepairOrderControllerService")
@RequiredArgsConstructor
public class RepairOrderControllerService  {

    private final IRepairOrderService repairOrderService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( RepairOrderPageVO pageVO){
        IPage<RepairOrder> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<RepairOrder> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<RepairOrder> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(RepairOrder::getRepairOrderId);

            if(pageVO.getRepairOrderId() != null){
                queryWrapper.eq(RepairOrder::getRepairOrderId, pageVO.getRepairOrderId());
            }


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

            return ResultUtils.success(repairOrderService.page(page, queryWrapper));
        }


        public BaseResponse list(  RepairOrderVO vo){
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
                return ResultUtils.success(repairOrderService.list(queryWrapper));
            }


            @SensitiveFilter
            public BaseResponse add(  RepairOrderVO vo) {
                RepairOrder entity =  RepairOrder.builder()
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .content(vo.getContent())
                    .repairStatusEnumRepairStatusEnumId1(vo.getRepairStatusEnumRepairStatusEnumId1())
                    .feedback(vo.getFeedback())
                .build();







                boolean  f = transactionTemplate.execute(status -> {
                    try {

                boolean flag =this.repairOrderService.save(entity);


                final List<StaticResource> ImageFilesImage = vo.getImageFiles();
                if (ImageFilesImage  != null && !ImageFilesImage .isEmpty()) {
                    List<StaticResource> validImages = new ArrayList<>();
                    for (StaticResource i : ImageFilesImage) {
                        i.setRelateTableColumnName("image_files");
                        i.setRelatedTableName("repair_order");
                        i.setResourceId(null);
                        i.setRelatedTableKey(entity.getRepairOrderId());
                        
                        String resourcePath = i.getResourcePath();
                        String resourceName = i.getResourceName();
                        
                        // 确保 resourcePath 不为空
                        if (StringUtils.isEmpty(resourcePath)) {
                            continue;
                        }
                        
                        // 处理 resourcePath：确保格式正确
                        // 前端上传的路径已经是正确的相对路径，如 /profile/upload/2025/11/03/xxx.jpg
                        if (!resourcePath.startsWith("/")) {
                            resourcePath = "/" + resourcePath;
                        }
                        i.setResourcePath(resourcePath);
                        
                        // 确保 resourceName 有值
                        if (StringUtils.isEmpty(resourceName)) {
                            if (resourcePath.contains("/")) {
                                resourceName = resourcePath.substring(resourcePath.lastIndexOf("/") + 1);
                            } else {
                                resourceName = resourcePath;
                            }
                            i.setResourceName(resourceName);
                        }
                        validImages.add(i);
                    }
                    if (!validImages.isEmpty()) {
                        staticResourceService.saveBatch(validImages);
                    }
                }




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
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("repair_order").data(entity).build());
                }

                return
                                f
                              
                ? ResultUtils.success(entity.getRepairOrderId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( RepairOrderVO vo) {
                RepairOrder entity =  RepairOrder.builder()
                    .repairOrderId(vo.getRepairOrderId())
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .content(vo.getContent())
                    .repairStatusEnumRepairStatusEnumId1(vo.getRepairStatusEnumRepairStatusEnumId1())
                    .feedback(vo.getFeedback())
                .build();


                boolean  f = transactionTemplate.execute(status -> {
                    try {

                        boolean flag = this.repairOrderService.saveOrUpdate(entity);



                        final List<StaticResource> ImageFilesImage = vo.getImageFiles();

                        if (ImageFilesImage  != null) {
                        final LambdaQueryWrapper<StaticResource> image_filesqueryWrapper = new LambdaQueryWrapper<>();
                        image_filesqueryWrapper.eq(StaticResource::getRelatedTableName,"repair_order").eq(StaticResource::getRelateTableColumnName,"image_files").eq(StaticResource::getRelatedTableKey,entity.getRepairOrderId());
                        staticResourceService.remove(image_filesqueryWrapper);
                        }


                        if (ImageFilesImage  != null && !ImageFilesImage .isEmpty()) {
                            ImageFilesImage.forEach(i -> {
                                i.setRelateTableColumnName("image_files");
                                i.setRelatedTableName("repair_order");
                                i.setRelatedTableKey(entity.getRepairOrderId());
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
                         ? ResultUtils.success(entity.getRepairOrderId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( RepairOrderVO vo ) {
                RepairOrder entity = this.repairOrderService.getById(vo.getRepairOrderId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( RepairOrderVO vo) {
                boolean flag = this.repairOrderService.removeById(vo.getRepairOrderId());




                final LambdaQueryWrapper<StaticResource> image_filesqueryWrapper = new LambdaQueryWrapper<>();
                image_filesqueryWrapper.eq(StaticResource::getRelatedTableName,"repair_order").eq(StaticResource::getRelateTableColumnName,"image_files").eq(StaticResource::getRelatedTableKey,vo.getRepairOrderId());
                staticResourceService.remove(image_filesqueryWrapper);


                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

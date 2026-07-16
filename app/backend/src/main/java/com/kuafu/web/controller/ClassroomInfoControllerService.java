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
import com.kuafu.web.entity.ClassroomInfo;
import com.kuafu.web.service.IClassroomInfoService;
import com.kuafu.web.vo.ClassroomInfoPageVO;
import com.kuafu.web.vo.ClassroomInfoVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 教室信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("ClassroomInfoControllerService")
@RequiredArgsConstructor
public class ClassroomInfoControllerService  {

    private final IClassroomInfoService classroomInfoService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( ClassroomInfoPageVO pageVO){
        IPage<ClassroomInfo> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<ClassroomInfo> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<ClassroomInfo> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(ClassroomInfo::getClassroomInfoId);

            if(pageVO.getClassroomInfoId() != null){
                queryWrapper.eq(ClassroomInfo::getClassroomInfoId, pageVO.getClassroomInfoId());
            }


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


        public BaseResponse list(  ClassroomInfoVO vo){
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


            @SensitiveFilter
            public BaseResponse add(  ClassroomInfoVO vo) {
                ClassroomInfo entity =  ClassroomInfo.builder()
                    .buildingEnumBuildingEnumId1(vo.getBuildingEnumBuildingEnumId1())
                    .roomNumber(vo.getRoomNumber())
                    .qrCodePath(vo.getQrCodePath())
                .build();








                boolean flag =this.classroomInfoService.save(entity);






                if(flag
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("classroom_info").data(entity).build());
                }

                return
flag

                ? ResultUtils.success(entity.getClassroomInfoId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( ClassroomInfoVO vo) {
                ClassroomInfo entity =  ClassroomInfo.builder()
                    .classroomInfoId(vo.getClassroomInfoId())
                    .buildingEnumBuildingEnumId1(vo.getBuildingEnumBuildingEnumId1())
                    .roomNumber(vo.getRoomNumber())
                    .qrCodePath(vo.getQrCodePath())
                .build();



                        boolean flag = this.classroomInfoService.saveOrUpdate(entity);








                return  flag
                         ? ResultUtils.success(entity.getClassroomInfoId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( ClassroomInfoVO vo ) {
                ClassroomInfo entity = this.classroomInfoService.getById(vo.getClassroomInfoId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( ClassroomInfoVO vo) {
                boolean flag = this.classroomInfoService.removeById(vo.getClassroomInfoId());





                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

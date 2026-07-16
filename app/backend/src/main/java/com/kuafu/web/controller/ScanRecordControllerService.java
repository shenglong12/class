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
import com.kuafu.web.entity.ScanRecord;
import com.kuafu.web.service.IScanRecordService;
import com.kuafu.web.vo.ScanRecordPageVO;
import com.kuafu.web.vo.ScanRecordVO;
import com.kuafu.common.util.QueryUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * <p> 扫码记录 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Service("ScanRecordControllerService")
@RequiredArgsConstructor
public class ScanRecordControllerService  {

    private final IScanRecordService scanRecordService;

    private final IStaticResourceService staticResourceService;

    private final TransactionTemplate transactionTemplate;

    private final MyEventService myEventService;



    public BaseResponse page( ScanRecordPageVO pageVO){
        IPage<ScanRecord> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());

         final QueryWrapper<ScanRecord> objectQueryWrapper = new QueryWrapper<>();

        QueryUtils.getQueryWrapper(objectQueryWrapper,pageVO);
        LambdaQueryWrapper<ScanRecord> queryWrapper = objectQueryWrapper.lambda();
        queryWrapper.orderByDesc(ScanRecord::getScanId);

            if(pageVO.getScanId() != null){
                queryWrapper.eq(ScanRecord::getScanId, pageVO.getScanId());
            }


            if(pageVO.getUserInfoUserInfoId1() != null){
                queryWrapper.eq(ScanRecord::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
            }


        if(StringUtils.isNotEmpty(pageVO.getBuildingName())) {
//                        queryWrapper.like(ScanRecord::getBuildingName, pageVO.getBuildingName());
                        String s_string = pageVO.getBuildingName();
                String[] strings = s_string.split(" ");
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < strings.length; i++) {
                        String s = strings[i];
                        if (StringUtils.isNotEmpty(s)) {
                            final String trim = s.trim();
                            wrapper.like(ScanRecord::getBuildingName, trim);
                            if (i != strings.length - 1) {
                                wrapper.or();
                            }
                        }
                    }
                });
            }


        if(StringUtils.isNotEmpty(pageVO.getRoomNumber())) {
                queryWrapper.eq(ScanRecord::getRoomNumber, pageVO.getRoomNumber());
            }


            if(pageVO.getScanTime() != null){
                queryWrapper.eq(ScanRecord::getScanTime, pageVO.getScanTime());
            }

            return ResultUtils.success(scanRecordService.page(page, queryWrapper));
        }


        public BaseResponse list(  ScanRecordVO vo){
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


            @SensitiveFilter
            public BaseResponse add(  ScanRecordVO vo) {
                ScanRecord entity =  ScanRecord.builder()
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .buildingName(vo.getBuildingName())
                    .roomNumber(vo.getRoomNumber())
                    .scanTime(vo.getScanTime())
                .build();








                boolean flag =this.scanRecordService.save(entity);






                if(flag
                        ){
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("scan_record").data(entity).build());
                }

                return
flag

                ? ResultUtils.success(entity.getScanId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }


            @SensitiveFilter
            public BaseResponse update( ScanRecordVO vo) {
                ScanRecord entity =  ScanRecord.builder()
                    .scanId(vo.getScanId())
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .buildingName(vo.getBuildingName())
                    .roomNumber(vo.getRoomNumber())
                    .scanTime(vo.getScanTime())
                .build();



                        boolean flag = this.scanRecordService.saveOrUpdate(entity);








                return  flag
                         ? ResultUtils.success(entity.getScanId()) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse get( ScanRecordVO vo ) {
                ScanRecord entity = this.scanRecordService.getById(vo.getScanId());
                return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }



            public BaseResponse delete( ScanRecordVO vo) {
                boolean flag = this.scanRecordService.removeById(vo.getScanId());





                return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
            }

        }

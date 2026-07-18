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
import com.kuafu.web.entity.BookingInfo;
import com.kuafu.web.service.IBookingInfoService;
import com.kuafu.web.vo.BookingInfoPageVO;
import com.kuafu.web.vo.BookingInfoVO;
import com.kuafu.web.event.MyEventService;
import com.kuafu.web.event.EventVo;
/**
 * <p> 预约信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookingInfo")
@Api(tags = {"预约信息"})
public class BookingInfoController  {

    private final IBookingInfoService bookingInfoService;

    private final MyEventService myEventService;

    private final ExcelProvider excelProvider;
    private final IStaticResourceService staticResourceService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page( @RequestBody BookingInfoPageVO pageVO){
        IPage<BookingInfo> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

            if(pageVO.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(BookingInfo::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
        }
            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(BookingInfo::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
        }
            if(pageVO.getBookingDate() != null){
            queryWrapper.eq(BookingInfo::getBookingDate, pageVO.getBookingDate());
        }
            if(StringUtils.isNotEmpty(pageVO.getTimePeriod())) {
            queryWrapper.eq(BookingInfo::getTimePeriod, pageVO.getTimePeriod());
        }
            if(StringUtils.isNotEmpty(pageVO.getPurpose())) {
            queryWrapper.eq(BookingInfo::getPurpose, pageVO.getPurpose());
        }
            if(pageVO.getBookingStatusEnumBookingStatusEnumId1() != null){
            queryWrapper.eq(BookingInfo::getBookingStatusEnumBookingStatusEnumId1, pageVO.getBookingStatusEnumBookingStatusEnumId1());
        }
            if(pageVO.getCreateTime() != null){
            queryWrapper.eq(BookingInfo::getCreateTime, pageVO.getCreateTime());
        }
            if(StringUtils.isNotEmpty(pageVO.getStartTime())) {
            queryWrapper.eq(BookingInfo::getStartTime, pageVO.getStartTime());
        }
            if(StringUtils.isNotEmpty(pageVO.getEndTime())) {
            queryWrapper.eq(BookingInfo::getEndTime, pageVO.getEndTime());
        }
        return ResultUtils.success(bookingInfoService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list( @RequestBody BookingInfoVO vo){
        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

                if(vo.getUserInfoUserInfoId1() != null){
            queryWrapper.eq(BookingInfo::getUserInfoUserInfoId1, vo.getUserInfoUserInfoId1());
        }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(BookingInfo::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
        }
                if(vo.getBookingDate() != null){
            queryWrapper.eq(BookingInfo::getBookingDate, vo.getBookingDate());
        }
            if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
            queryWrapper.eq(BookingInfo::getTimePeriod, vo.getTimePeriod());
        }
            if(StringUtils.isNotEmpty(vo.getPurpose())) {
            queryWrapper.eq(BookingInfo::getPurpose, vo.getPurpose());
        }
                if(vo.getBookingStatusEnumBookingStatusEnumId1() != null){
            queryWrapper.eq(BookingInfo::getBookingStatusEnumBookingStatusEnumId1, vo.getBookingStatusEnumBookingStatusEnumId1());
        }
                if(vo.getCreateTime() != null){
            queryWrapper.eq(BookingInfo::getCreateTime, vo.getCreateTime());
        }
            if(StringUtils.isNotEmpty(vo.getStartTime())) {
            queryWrapper.eq(BookingInfo::getStartTime, vo.getStartTime());
        }
            if(StringUtils.isNotEmpty(vo.getEndTime())) {
            queryWrapper.eq(BookingInfo::getEndTime, vo.getEndTime());
        }
        return ResultUtils.success(bookingInfoService.list(queryWrapper));
    }


            @PostMapping("get_select_list")
            @ApiOperation("获取所有的下拉列表")
            public BaseResponse get_select_list( ){
                LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(BookingInfo::getBookingInfoId);

        List<BookingInfo> list =bookingInfoService.list(queryWrapper);
        final List<SelectVO> selectVOS = list.stream().map(l -> new SelectVO(l.getBookingInfoId(), l.getBookingInfoId().toString())).collect(Collectors.toList());
        return ResultUtils.success(selectVOS);

                }

    @PostMapping("add")
    @ApiOperation("新增")
    @SensitiveFilter
    public BaseResponse add( @RequestBody BookingInfoVO vo) {
        BookingInfo entity =  BookingInfo.builder()
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .bookingDate(vo.getBookingDate())
                    .timePeriod(vo.getTimePeriod())
                    .purpose(vo.getPurpose())
                    .bookingStatusEnumBookingStatusEnumId1(vo.getBookingStatusEnumBookingStatusEnumId1())
                    .startTime(vo.getStartTime())
                    .endTime(vo.getEndTime())
                .build();
        boolean flag =this.bookingInfoService.save(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("bookingInfo").data(entity).build());
                }


        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    @SensitiveFilter
    public BaseResponse update( @RequestBody BookingInfoVO vo) {
        BookingInfo entity =  BookingInfo.builder()
                    .bookingInfoId(vo.getBookingInfoId())
                    .userInfoUserInfoId1(vo.getUserInfoUserInfoId1())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .bookingDate(vo.getBookingDate())
                    .timePeriod(vo.getTimePeriod())
                    .purpose(vo.getPurpose())
                    .bookingStatusEnumBookingStatusEnumId1(vo.getBookingStatusEnumBookingStatusEnumId1())
                    .startTime(vo.getStartTime())
                    .endTime(vo.getEndTime())
                .build();
        boolean flag = this.bookingInfoService.updateById(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("update").tableName("bookingInfo").data(entity).build());
                }



        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer bookingInfoId) {
        BookingInfo entity = this.bookingInfoService.getById(bookingInfoId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value="id") Integer bookingInfoId) {
        boolean flag = this.bookingInfoService.removeById(bookingInfoId);
        if (flag) {
            myEventService.publishEvent(EventVo.builder().model("delete").tableName("bookingInfo").data( BookingInfo.builder().bookingInfoId(bookingInfoId).build() ).build());
        }
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody BookingInfoPageVO pageVO) {

        LambdaQueryWrapper<BookingInfo> queryWrapper = new LambdaQueryWrapper<>();

                        if(pageVO.getUserInfoUserInfoId1() != null){
                            queryWrapper.eq(BookingInfo::getUserInfoUserInfoId1, pageVO.getUserInfoUserInfoId1());
                        }
                        if(pageVO.getClassroomInfoClassroomInfoId1() != null){
                            queryWrapper.eq(BookingInfo::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
                        }
                        if(pageVO.getBookingDate() != null){
                            queryWrapper.eq(BookingInfo::getBookingDate, pageVO.getBookingDate());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getTimePeriod())) {
                            queryWrapper.eq(BookingInfo::getTimePeriod, pageVO.getTimePeriod());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getPurpose())) {
                            queryWrapper.eq(BookingInfo::getPurpose, pageVO.getPurpose());
                        }
                        if(pageVO.getBookingStatusEnumBookingStatusEnumId1() != null){
                            queryWrapper.eq(BookingInfo::getBookingStatusEnumBookingStatusEnumId1, pageVO.getBookingStatusEnumBookingStatusEnumId1());
                        }
                        if(pageVO.getCreateTime() != null){
                            queryWrapper.eq(BookingInfo::getCreateTime, pageVO.getCreateTime());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getStartTime())) {
                            queryWrapper.eq(BookingInfo::getStartTime, pageVO.getStartTime());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getEndTime())) {
                            queryWrapper.eq(BookingInfo::getEndTime, pageVO.getEndTime());
                        }
            List<BookingInfo> list = bookingInfoService.list(queryWrapper);
            ExcelUtil<BookingInfo> util = new ExcelUtil<>(BookingInfo.class);
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
            excelProvider.pdfData(file, BookingInfo.class, bookingInfoService::saveBatch);
        }
        else{
            excelProvider.importData(file, BookingInfo.class, bookingInfoService::saveBatch);
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
                        Workbook workbook = excelProvider.downloadExcelTemplate(BookingInfo.class);
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
        boolean flag = this.bookingInfoService.removeByIds(ids);
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }
}

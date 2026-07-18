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
import com.kuafu.web.entity.CourseInfo;
import com.kuafu.web.service.ICourseInfoService;
import com.kuafu.web.vo.CourseInfoPageVO;
import com.kuafu.web.vo.CourseInfoVO;
import com.kuafu.web.event.MyEventService;
import com.kuafu.web.event.EventVo;
/**
 * <p> 课程信息 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/courseInfo")
@Api(tags = {"课程信息"})
public class CourseInfoController  {

    private final ICourseInfoService courseInfoService;

    private final MyEventService myEventService;

    private final ExcelProvider excelProvider;
    private final SqliteSequenceReset sequenceReset;
    private final IStaticResourceService staticResourceService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page( @RequestBody CourseInfoPageVO pageVO){
        IPage<CourseInfo> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(pageVO.getCourseName())) {
            queryWrapper.like(CourseInfo::getCourseName, pageVO.getCourseName());
        }
            if(StringUtils.isNotEmpty(pageVO.getCollege())) {
            queryWrapper.eq(CourseInfo::getCollege, pageVO.getCollege());
        }
            if(StringUtils.isNotEmpty(pageVO.getMajor())) {
            queryWrapper.eq(CourseInfo::getMajor, pageVO.getMajor());
        }
            if(pageVO.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(CourseInfo::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
        }
            if(pageVO.getStartDate() != null){
            queryWrapper.eq(CourseInfo::getStartDate, pageVO.getStartDate());
        }
            if(pageVO.getEndDate() != null){
            queryWrapper.eq(CourseInfo::getEndDate, pageVO.getEndDate());
        }
            if(StringUtils.isNotEmpty(pageVO.getWeekdays())) {
            queryWrapper.eq(CourseInfo::getWeekdays, pageVO.getWeekdays());
        }
            if(StringUtils.isNotEmpty(pageVO.getTimePeriod())) {
            queryWrapper.eq(CourseInfo::getTimePeriod, pageVO.getTimePeriod());
        }
            if(StringUtils.isNotEmpty(pageVO.getContent())) {
            queryWrapper.eq(CourseInfo::getContent, pageVO.getContent());
        }
        return ResultUtils.success(courseInfoService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list( @RequestBody CourseInfoVO vo){
        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getCourseName())) {
            queryWrapper.like(CourseInfo::getCourseName, vo.getCourseName());
        }
            if(StringUtils.isNotEmpty(vo.getCollege())) {
            queryWrapper.eq(CourseInfo::getCollege, vo.getCollege());
        }
            if(StringUtils.isNotEmpty(vo.getMajor())) {
            queryWrapper.eq(CourseInfo::getMajor, vo.getMajor());
        }
                if(vo.getClassroomInfoClassroomInfoId1() != null){
            queryWrapper.eq(CourseInfo::getClassroomInfoClassroomInfoId1, vo.getClassroomInfoClassroomInfoId1());
        }
                if(vo.getStartDate() != null){
            queryWrapper.eq(CourseInfo::getStartDate, vo.getStartDate());
        }
                if(vo.getEndDate() != null){
            queryWrapper.eq(CourseInfo::getEndDate, vo.getEndDate());
        }
            if(StringUtils.isNotEmpty(vo.getWeekdays())) {
            queryWrapper.eq(CourseInfo::getWeekdays, vo.getWeekdays());
        }
            if(StringUtils.isNotEmpty(vo.getTimePeriod())) {
            queryWrapper.eq(CourseInfo::getTimePeriod, vo.getTimePeriod());
        }
            if(StringUtils.isNotEmpty(vo.getContent())) {
            queryWrapper.eq(CourseInfo::getContent, vo.getContent());
        }
        return ResultUtils.success(courseInfoService.list(queryWrapper));
    }


            @PostMapping("get_select_list")
            @ApiOperation("获取所有的下拉列表")
            public BaseResponse get_select_list( ){
                LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(CourseInfo::getCourseInfoId);

        List<CourseInfo> list =courseInfoService.list(queryWrapper);
        final List<SelectVO> selectVOS = list.stream().map(l -> new SelectVO(l.getCourseInfoId(), l.getCourseInfoId().toString())).collect(Collectors.toList());
        return ResultUtils.success(selectVOS);

                }

    @PostMapping("add")
    @ApiOperation("新增")
    @SensitiveFilter
    public BaseResponse add( @RequestBody CourseInfoVO vo) {
        CourseInfo entity =  CourseInfo.builder()
                    .courseName(vo.getCourseName())
                    .college(vo.getCollege())
                    .major(vo.getMajor())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .startDate(vo.getStartDate())
                    .endDate(vo.getEndDate())
                    .weekdays(vo.getWeekdays())
                    .timePeriod(vo.getTimePeriod())
                    .content(vo.getContent())
                .build();
        boolean flag =this.courseInfoService.save(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("courseInfo").data(entity).build());
                }


        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    @SensitiveFilter
    public BaseResponse update( @RequestBody CourseInfoVO vo) {
        CourseInfo entity =  CourseInfo.builder()
                    .courseInfoId(vo.getCourseInfoId())
                    .courseName(vo.getCourseName())
                    .college(vo.getCollege())
                    .major(vo.getMajor())
                    .classroomInfoClassroomInfoId1(vo.getClassroomInfoClassroomInfoId1())
                    .startDate(vo.getStartDate())
                    .endDate(vo.getEndDate())
                    .weekdays(vo.getWeekdays())
                    .timePeriod(vo.getTimePeriod())
                    .content(vo.getContent())
                .build();
        boolean flag = this.courseInfoService.updateById(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("update").tableName("courseInfo").data(entity).build());
                }



        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer courseInfoId) {
        CourseInfo entity = this.courseInfoService.getById(courseInfoId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value="id") Integer courseInfoId) {
        boolean flag = this.courseInfoService.removeById(courseInfoId);
        if (flag) {
            myEventService.publishEvent(EventVo.builder().model("delete").tableName("courseInfo").data( CourseInfo.builder().courseInfoId(courseInfoId).build() ).build());
        }
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody CourseInfoPageVO pageVO) {

        LambdaQueryWrapper<CourseInfo> queryWrapper = new LambdaQueryWrapper<>();

                    if(StringUtils.isNotEmpty(pageVO.getCourseName())) {
                                    queryWrapper.like(CourseInfo::getCourseName, pageVO.getCourseName());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getCollege())) {
                            queryWrapper.eq(CourseInfo::getCollege, pageVO.getCollege());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getMajor())) {
                            queryWrapper.eq(CourseInfo::getMajor, pageVO.getMajor());
                        }
                        if(pageVO.getClassroomInfoClassroomInfoId1() != null){
                            queryWrapper.eq(CourseInfo::getClassroomInfoClassroomInfoId1, pageVO.getClassroomInfoClassroomInfoId1());
                        }
                        if(pageVO.getStartDate() != null){
                            queryWrapper.eq(CourseInfo::getStartDate, pageVO.getStartDate());
                        }
                        if(pageVO.getEndDate() != null){
                            queryWrapper.eq(CourseInfo::getEndDate, pageVO.getEndDate());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getWeekdays())) {
                            queryWrapper.eq(CourseInfo::getWeekdays, pageVO.getWeekdays());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getTimePeriod())) {
                            queryWrapper.eq(CourseInfo::getTimePeriod, pageVO.getTimePeriod());
                        }
                    if(StringUtils.isNotEmpty(pageVO.getContent())) {
                            queryWrapper.eq(CourseInfo::getContent, pageVO.getContent());
                        }
            List<CourseInfo> list = courseInfoService.list(queryWrapper);
            ExcelUtil<CourseInfo> util = new ExcelUtil<>(CourseInfo.class);
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
            excelProvider.pdfData(file, CourseInfo.class, courseInfoService::save);
        }
        else{
            excelProvider.importData(file, CourseInfo.class, courseInfoService::save);
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
                        Workbook workbook = excelProvider.downloadExcelTemplate(CourseInfo.class);
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
        boolean flag = this.courseInfoService.removeByIds(ids);
        sequenceReset.resetIfEmpty("course_info");
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }
}

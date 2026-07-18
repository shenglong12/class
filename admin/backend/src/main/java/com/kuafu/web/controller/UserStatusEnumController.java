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
import com.kuafu.web.entity.UserStatusEnum;
import com.kuafu.web.service.IUserStatusEnumService;
import com.kuafu.web.vo.UserStatusEnumPageVO;
import com.kuafu.web.vo.UserStatusEnumVO;
import com.kuafu.web.event.MyEventService;
import com.kuafu.web.event.EventVo;
/**
 * <p> 用户状态 接口 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/userStatusEnum")
@Api(tags = {"用户状态"})
public class UserStatusEnumController  {

    private final IUserStatusEnumService userStatusEnumService;

    private final MyEventService myEventService;

    private final ExcelProvider excelProvider;
    private final SqliteSequenceReset sequenceReset;
    private final IStaticResourceService staticResourceService;

    @PostMapping("page")
    @ApiOperation("分页")
    public BaseResponse page( @RequestBody UserStatusEnumPageVO pageVO){
        IPage<UserStatusEnum> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(pageVO.getStatusName())) {
            queryWrapper.like(UserStatusEnum::getStatusName, pageVO.getStatusName());
        }
        return ResultUtils.success(userStatusEnumService.page(page, queryWrapper));
    }

    @PostMapping("list")
    @ApiOperation("列表")
    public BaseResponse list( @RequestBody UserStatusEnumVO vo){
        LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

            if(StringUtils.isNotEmpty(vo.getStatusName())) {
            queryWrapper.like(UserStatusEnum::getStatusName, vo.getStatusName());
        }
        return ResultUtils.success(userStatusEnumService.list(queryWrapper));
    }


            @PostMapping("get_select_list")
            @ApiOperation("获取所有的下拉列表")
            public BaseResponse get_select_list( ){
                LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(UserStatusEnum::getUserStatusEnumId);

        List<UserStatusEnum> list =userStatusEnumService.list(queryWrapper);
        final List<SelectVO> selectVOS = list.stream().map(l -> new SelectVO(l.getUserStatusEnumId(), l.getUserStatusEnumId().toString())).collect(Collectors.toList());
        return ResultUtils.success(selectVOS);

                }

    @PostMapping("add")
    @ApiOperation("新增")
    @SensitiveFilter
    public BaseResponse add( @RequestBody UserStatusEnumVO vo) {
        UserStatusEnum entity =  UserStatusEnum.builder()
                    .statusName(vo.getStatusName())
                .build();
        boolean flag =this.userStatusEnumService.save(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("add").tableName("userStatusEnum").data(entity).build());
                }


        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    @SensitiveFilter
    public BaseResponse update( @RequestBody UserStatusEnumVO vo) {
        UserStatusEnum entity =  UserStatusEnum.builder()
                    .userStatusEnumId(vo.getUserStatusEnumId())
                    .statusName(vo.getStatusName())
                .build();
        boolean flag = this.userStatusEnumService.updateById(entity);
                if (flag) {
                    myEventService.publishEvent(EventVo.builder().model("update").tableName("userStatusEnum").data(entity).build());
                }



        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @GetMapping("get/{id}")
    @ApiOperation("根据Id查询")
    public BaseResponse get(@PathVariable(value="id") Integer userStatusEnumId) {
        UserStatusEnum entity = this.userStatusEnumService.getById(userStatusEnumId);
        return entity!=null ? ResultUtils.success(entity) : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }


    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public BaseResponse delete(@PathVariable(value="id") Integer userStatusEnumId) {
        boolean flag = this.userStatusEnumService.removeById(userStatusEnumId);
        if (flag) {
            myEventService.publishEvent(EventVo.builder().model("delete").tableName("userStatusEnum").data( UserStatusEnum.builder().userStatusEnumId(userStatusEnumId).build() ).build());
        }
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }

    /**
     * 导出excel
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody UserStatusEnumPageVO pageVO) {

        LambdaQueryWrapper<UserStatusEnum> queryWrapper = new LambdaQueryWrapper<>();

                    if(StringUtils.isNotEmpty(pageVO.getStatusName())) {
                                    queryWrapper.like(UserStatusEnum::getStatusName, pageVO.getStatusName());
                        }
            List<UserStatusEnum> list = userStatusEnumService.list(queryWrapper);
            ExcelUtil<UserStatusEnum> util = new ExcelUtil<>(UserStatusEnum.class);
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
            excelProvider.pdfData(file, UserStatusEnum.class, entity -> {
            LambdaQueryWrapper<UserStatusEnum> qw = new LambdaQueryWrapper<>();
            qw.eq(UserStatusEnum::getStatusName, entity.getStatusName());
            if (userStatusEnumService.count(qw) == 0) {
                userStatusEnumService.save(entity);
            }
        });
        }
        else{
            excelProvider.importData(file, UserStatusEnum.class, entity -> {
            LambdaQueryWrapper<UserStatusEnum> qw = new LambdaQueryWrapper<>();
            qw.eq(UserStatusEnum::getStatusName, entity.getStatusName());
            if (userStatusEnumService.count(qw) == 0) {
                userStatusEnumService.save(entity);
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
                        Workbook workbook = excelProvider.downloadExcelTemplate(UserStatusEnum.class);
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
        boolean flag = this.userStatusEnumService.removeByIds(ids);
        sequenceReset.resetIfEmpty("user_status_enum");
        return flag ? ResultUtils.success() : ResultUtils.error(ErrorCode.OPERATION_ERROR);
    }
}

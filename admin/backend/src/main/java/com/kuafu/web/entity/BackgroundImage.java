package com.kuafu.web.entity;

import java.util.Date;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuafu.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;
import com.kuafu.common.annotation.Excel.*;



/**
 * <p>  背景图片 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("background_image")
public class BackgroundImage    {
    @TableId(value = "background_image_id", type = IdType.AUTO)
    @JsonProperty(value = "backgroundImageId")
    @Excel(cellType = ColumnType.IMAGE, name = "主键")

    private Integer backgroundImageId;
    @JsonProperty(value = "userInfoUserInfoId1")
    @Excel(name = "上传管理员")
    @ExcelProperty(value = "上传管理员")
    @TableField(value = "user_info_user_info_id_1")

    private Integer userInfoUserInfoId1;
    @JsonProperty(value = "imageFilesResourceKey")
    @Excel(cellType = ColumnType.IMAGE, name = "背景图片")
    @ExcelProperty(value = "背景图片")
    @TableField(value = "image_files_resource_key")

    private String imageFilesResourceKey;
    @JsonProperty(value = "uploadTime")
    @Excel(dateFormat = "yyyy-MM-dd HH:mm:ss", name = "上传时间")
    @ExcelProperty(value = "上传时间")
    @TableField(value = "upload_time")

    private Date uploadTime;

    @TableField(exist = false)
    private List<StaticResource> imageFiles;

}

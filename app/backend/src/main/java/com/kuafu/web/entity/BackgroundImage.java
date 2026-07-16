package com.kuafu.web.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.*;
import com.kuafu.common.entity.StaticResource;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kuafu.web.annotation.IsNotNullField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import com.kuafu.common.annotation.*;
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
public class BackgroundImage  {
    @TableId(value = "background_image_id", type = IdType.AUTO)
    @JsonProperty("background_image_id")
    
    
    
    private Integer backgroundImageId;
    @JsonProperty("user_info_user_info_id_1")
    @IsNotNullField(description = "上传管理员")
    
    
    @TableField("user_info_user_info_id_1")
    private Integer userInfoUserInfoId1;
    @JsonProperty("upload_time")
    
    
    @TableField("upload_time")
    private Date uploadTime;



@TableField(exist = false)
@JsonProperty("image_files")
private List<StaticResource> imageFiles;



}

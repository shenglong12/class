package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

/**
 * <p>背景图片</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackgroundImageVO  {

     @JsonProperty(value = "backgroundImageId")
    private Integer backgroundImageId;
     @JsonProperty(value = "userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
     @JsonProperty(value = "imageFilesResourceKey")
    private String imageFilesResourceKey;
     @JsonProperty(value = "uploadTime")
    private Date uploadTime;

    @JsonProperty("imageFiles")
    private List<StaticResource> imageFiles;

}

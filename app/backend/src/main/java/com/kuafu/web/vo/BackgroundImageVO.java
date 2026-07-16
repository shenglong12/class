package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.kuafu.common.entity.StaticResource;
import java.util.List;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kuafu.common.deserializer.MultiDateDeserializer;
import com.kuafu.common.entity.BaseEntity;
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
public class BackgroundImageVO  extends BaseEntity {

    @JsonProperty("backgroundImageId")
    private Integer backgroundImageId;
    @JsonProperty("userInfoUserInfoId1")
    private Integer userInfoUserInfoId1;
    @JsonDeserialize(using = MultiDateDeserializer.class)
    @JsonProperty("uploadTime")
    private Date uploadTime;


    private List<StaticResource> imageFiles;


}

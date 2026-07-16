package com.kuafu.web.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kuafu.common.deserializer.MultiDateDeserializer;
import com.kuafu.common.domin.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;
import com.kuafu.common.entity.StaticResource;
import com.kuafu.common.entity.BaseEntity;
/**
 * <p>系统配置-分页列表-响应参数</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KfSystemConfigPageVO extends BaseEntity {

@JsonProperty("id")
    private Integer id;
@JsonProperty("name")
    private String name;
@JsonProperty("chineseName")
    private String chineseName;
@JsonProperty("description")
    private String description;
@JsonProperty("content")
    private String content;
@JsonProperty("remark")
    private String remark;
@JsonProperty("type")
    private String type;


}

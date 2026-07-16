package com.kuafu.web.vo;

import java.util.Date;

import com.kuafu.common.domin.PageRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class KfSystemConfigPageVO extends PageRequest {

    @JsonProperty(value = "id")
    private Integer id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "chineseName")
    private String chineseName;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "content")
    private String content;
    @JsonProperty(value = "remark")
    private String remark;
    @JsonProperty(value = "type")
    private String type;

}

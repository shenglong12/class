package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

/**
 * <p>系统配置</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KfSystemConfigVO  {

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

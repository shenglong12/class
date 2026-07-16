package com.kuafu.web.vo;

import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import lombok.NoArgsConstructor;
import com.kuafu.common.domin.StaticResource;
import java.util.List;

/**
 * <p>API配置</p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynamicApiSettingVO  {

     @JsonProperty(value = "id")
    private Integer id;
     @JsonProperty(value = "keyName")
    private String keyName;
     @JsonProperty(value = "description")
    private String description;
     @JsonProperty(value = "url")
    private String url;
     @JsonProperty(value = "token")
    private String token;
     @JsonProperty(value = "appId")
    private String appId;
     @JsonProperty(value = "apiKey")
    private String apiKey;
     @JsonProperty(value = "apiSecret")
    private String apiSecret;
     @JsonProperty(value = "method")
    private String method;
     @JsonProperty(value = "bodyType")
    private String bodyType;
     @JsonProperty(value = "bodyTemplate")
    private String bodyTemplate;
     @JsonProperty(value = "header")
    private String header;
     @JsonProperty(value = "authType")
    private String authType;
     @JsonProperty(value = "protocol")
    private String protocol;
     @JsonProperty(value = "dataPath")
    private String dataPath;
     @JsonProperty(value = "dataType")
    private String dataType;


}

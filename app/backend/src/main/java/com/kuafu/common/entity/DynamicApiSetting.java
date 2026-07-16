package com.kuafu.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "dynamic_api_setting")
public class DynamicApiSetting {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String keyName;
    private String description;
    private String url;
    private String token;
    private String appId;
    private String apiKey;
    private String apiSecret;
    private String method;
    private String bodyType;
    private String bodyTemplate;
    private String header;
    private String authType;
    private String protocol;

    private String dataPath;
    private String dataType;

}

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
 * <p>  API配置 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("dynamic_api_setting")
public class DynamicApiSetting    {
    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty(value = "id")
    @Excel(name = "主键")

    private Integer id;
    @JsonProperty(value = "keyName")
    @Excel(name = "api名称")
    @ExcelProperty(value = "api名称")
    @TableField(value = "key_name")

    private String keyName;
    @JsonProperty(value = "description")
    @Excel(name = "描述")
    @ExcelProperty(value = "描述")
    @TableField(value = "description")

    private String description;
    @JsonProperty(value = "url")
    @Excel(name = "地址")
    @ExcelProperty(value = "地址")
    @TableField(value = "url")

    private String url;
    @JsonProperty(value = "token")
    @Excel(name = "令牌")
    @ExcelProperty(value = "令牌")
    @TableField(value = "token")

    private String token;
    @JsonProperty(value = "appId")
    @Excel(name = "APPID")
    @ExcelProperty(value = "APPID")
    @TableField(value = "app_id")

    private String appId;
    @JsonProperty(value = "apiKey")
    @Excel(name = "令牌Key")
    @ExcelProperty(value = "令牌Key")
    @TableField(value = "api_key")

    private String apiKey;
    @JsonProperty(value = "apiSecret")
    @Excel(name = "令牌密钥")
    @ExcelProperty(value = "令牌密钥")
    @TableField(value = "api_secret")

    private String apiSecret;
    @JsonProperty(value = "method")
    @Excel(name = "方法")
    @ExcelProperty(value = "方法")
    @TableField(value = "method")

    private String method;
    @JsonProperty(value = "bodyType")
    @Excel(name = "请求体类型")
    @ExcelProperty(value = "请求体类型")
    @TableField(value = "body_type")

    private String bodyType;
    @JsonProperty(value = "bodyTemplate")
    @Excel(name = "请求体模版")
    @ExcelProperty(value = "请求体模版")
    @TableField(value = "body_template")

    private String bodyTemplate;
    @JsonProperty(value = "header")
    @Excel(name = "请求头模版")
    @ExcelProperty(value = "请求头模版")
    @TableField(value = "header")

    private String header;
    @JsonProperty(value = "authType")
    @Excel(name = "授权类型")
    @ExcelProperty(value = "授权类型")
    @TableField(value = "auth_type")

    private String authType;
    @JsonProperty(value = "protocol")
    @Excel(name = "请求协议")
    @ExcelProperty(value = "请求协议")
    @TableField(value = "protocol")

    private String protocol;
    @JsonProperty(value = "dataPath")
    @Excel(name = "数据获取路径")
    @ExcelProperty(value = "数据获取路径")
    @TableField(value = "data_path")

    private String dataPath;
    @JsonProperty(value = "dataType")
    @Excel(name = "数据类型")
    @ExcelProperty(value = "数据类型")
    @TableField(value = "data_type")

    private String dataType;


}

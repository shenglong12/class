package com.kuafu.common.domin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("static_resources") // 指定数据库中的表名
public class StaticResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "resource_id", type = IdType.AUTO) // 指定主键并且是自增
    private Integer resourceId;


    /**
     * 资源名称
     */
    @JsonProperty("name")
    private String resourceName;

    /**
     * 资源路径
     */
    @JsonProperty("url")
    private String resourcePath;

    /**
     * 关联表名称
     */
    private String relatedTableName;

    /**
     * 关联表主键值
     */
    private Integer relatedTableKey;

    private String relateTableColumnName;
}

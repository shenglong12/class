package com.kuafu.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@TableName("static_resources") // 指定数据库中的表名
public class StaticResource implements Serializable {

    private static final long serialVersionUID = 1L;

    public StaticResource() {
    }

    @JsonCreator
    public StaticResource(String url) {
        this.resourcePath = url;
    }

    @TableId(value = "resource_id", type = IdType.AUTO) // 指定主键并且是自增
    private Integer resourceId;


    @JsonProperty("type")
    private String resourceType;

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

    public String getRelateTableColumnName() {
        return relateTableColumnName;
    }

    public void setRelateTableColumnName(String relateTableColumnName) {
        this.relateTableColumnName = relateTableColumnName;
    }

    // Getter and Setter methods

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        if (resourceName == null || resourceName.isEmpty()) {
            String resourcePath = getResourcePath();
            if (resourcePath.contains("/")) {
                resourceName = resourcePath.substring(resourcePath.lastIndexOf("/") + 1);
                if (resourceName.contains("?")) {
                    resourceName = resourceName.substring(0, resourceName.indexOf("?"));
                }
            }
        }
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getRelatedTableName() {
        return relatedTableName;
    }

    public void setRelatedTableName(String relatedTableName) {
        this.relatedTableName = relatedTableName;
    }

    public Integer getRelatedTableKey() {
        return relatedTableKey;
    }

    public void setRelatedTableKey(Integer relatedTableKey) {
        this.relatedTableKey = relatedTableKey;
    }

    @Override
    public String toString() {
        return "StaticResource{" +
                "resourceId=" + resourceId +
                ", resourceName='" + resourceName + '\'' +
                ", resourcePath='" + resourcePath + '\'' +
                ", relatedTableName='" + relatedTableName + '\'' +
                ", relatedTableKey=" + relatedTableKey +
                '}';
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}

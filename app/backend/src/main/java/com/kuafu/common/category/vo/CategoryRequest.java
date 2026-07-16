package com.kuafu.common.category.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryRequest {
    private Integer parentId;
    @NotNull(message = "关联表不能为空")
    private String relevanceTable;
    @NotNull(message = "关联字段不能为空")
    private String relevanceTableColumn;
}

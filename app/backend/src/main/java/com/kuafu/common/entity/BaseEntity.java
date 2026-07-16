package com.kuafu.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kuafu.common.domin.PageRequest;
import lombok.Data;

import java.util.List;

@Data
public class BaseEntity extends PageRequest {
    @TableField(exist = false)
    private List<Filter> andFilter;
    @TableField(exist = false)
    private List<Filter> orFilter;
    @TableField(exist = false)
    private List<OrderCondition> orderConditions;
}

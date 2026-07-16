package com.kuafu.common.domin;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * 分页请求
 *
 * @author kuafui
 */
@Data
public class PageRequest {

    @TableField(exist = false)
    /**
     * 当前页号
     */
    private long current = 1;

    @TableField(exist = false)
    /**
     * 页面大小
     */

    private long pageSize = 10;
    @TableField(exist = false)
    private List<String>     other_search_condition;

}

package com.kuafu.common.category.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.common.category.domain.Category;
import com.kuafu.common.category.service.CategoryService;
import com.kuafu.common.category.vo.CategoryRequest;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ResultUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;


    /**
     * 查询分类
     * @param categoryRequest
     * @return
     */
    @PostMapping("list")
    public BaseResponse getCategoryByParentId(@Validated @RequestBody CategoryRequest categoryRequest) {
        final String relevanceTable = categoryRequest.getRelevanceTable();
        final String relevanceTableColumn = categoryRequest.getRelevanceTableColumn();
        Integer parentId = categoryRequest.getParentId();

        final LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getRelevanceTable, relevanceTable)
                .eq(Category::getRelevanceTableColumn, relevanceTableColumn);

        if (parentId==null){
            queryWrapper.isNull(Category::getParentId);
        }else {
            queryWrapper.eq(Category::getParentId,parentId);
        }
        final List<Category> list = categoryService.list(queryWrapper);

        return ResultUtils.success(list);

    }
}

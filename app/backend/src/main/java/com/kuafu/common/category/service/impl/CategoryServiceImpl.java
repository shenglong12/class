package com.kuafu.common.category.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuafu.common.category.domain.Category;
import com.kuafu.common.category.service.CategoryService;
import com.kuafu.common.category.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author www.macpe.cn
* @description 针对表【category】的数据库操作Service实现
* @createDate 2024-10-16 14:46:34
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}





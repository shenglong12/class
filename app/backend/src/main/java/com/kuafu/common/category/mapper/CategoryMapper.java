package com.kuafu.common.category.mapper;

import com.kuafu.common.category.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
/**
* @author www.macpe.cn
* @description 针对表【category】的数据库操作Mapper
* @createDate 2024-10-16 14:46:34
* @Entity com.kuafu.common.category.domain.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {

}





package com.kuafu.web.mapper;

import java.util.List;
import com.kuafu.web.entity.BackgroundImageAll;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

/**
 * <p> 背景图片 Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface BackgroundImageAllMapper extends BaseMapper<BackgroundImageAll> {
    public List<BackgroundImageAll> selectListNew(@Param("ew") Wrapper<BackgroundImageAll> queryWrapper);
}

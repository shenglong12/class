package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.BackgroundImage;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> background_image Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface BackgroundImageStatisticsMapper extends BaseMapper<BackgroundImage> {

                        List<Object> background_image_statistic_2cec2f0a_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> background_image_statistic_6d9fd8e7_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> background_image_statistic_db8eea91_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> background_image_upload_time_datetime_statistic_775bad3a_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> background_image_upload_time_datetime_statistic_401f3864_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> background_image_upload_time_datetime_statistic_f7bdec73_count(@Param("ew") Wrapper queryWrapper);

}

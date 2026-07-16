package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.CourseInfo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> course_info Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface CourseInfoStatisticsMapper extends BaseMapper<CourseInfo> {

                        List<Object> course_info_statistic_11f06534_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> course_info_statistic_43e99b84_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> course_info_statistic_6ba45514_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> course_info_start_date_datetime_statistic_799f21ac_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> course_info_start_date_datetime_statistic_40a54bd0_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> course_info_start_date_datetime_statistic_470d5ddb_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> course_info_end_date_datetime_statistic_9bbd1d33_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> course_info_end_date_datetime_statistic_f4a5cdc7_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> course_info_end_date_datetime_statistic_9844a0b8_count(@Param("ew") Wrapper queryWrapper);

}

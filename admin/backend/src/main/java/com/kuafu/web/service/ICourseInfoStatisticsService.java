package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.CourseInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  course_info 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface ICourseInfoStatisticsService extends IService<CourseInfo> {
        List<Object> course_info_statistic_11f06534_count(LambdaQueryWrapper queryWrapper);

        List<Object> course_info_statistic_43e99b84_count(LambdaQueryWrapper queryWrapper);

        List<Object> course_info_statistic_6ba45514_count(LambdaQueryWrapper queryWrapper);

        List<Object> course_info_start_date_datetime_statistic_799f21ac_count(LambdaQueryWrapper queryWrapper);

        List<Object> course_info_start_date_datetime_statistic_40a54bd0_count(LambdaQueryWrapper queryWrapper);

        List<Object> course_info_start_date_datetime_statistic_470d5ddb_count(LambdaQueryWrapper queryWrapper);

        List<Object> course_info_end_date_datetime_statistic_9bbd1d33_count(LambdaQueryWrapper queryWrapper);

        List<Object> course_info_end_date_datetime_statistic_f4a5cdc7_count(LambdaQueryWrapper queryWrapper);

        List<Object> course_info_end_date_datetime_statistic_9844a0b8_count(LambdaQueryWrapper queryWrapper);

}
